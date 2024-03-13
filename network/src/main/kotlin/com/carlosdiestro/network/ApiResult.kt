package com.carlosdiestro.network

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.etag

internal sealed interface ApiResult<out T> {
    data class Success<T>(
        val data: T,
        val expireDate: Long,
        val eTag: String
    ) : ApiResult<T>

    data object RedirectException : ApiResult<Nothing>

    data object NoDataAvailable : ApiResult<Nothing>
}

internal suspend inline fun <reified T> suspendRunCatching(
    request: () -> HttpResponse
): ApiResult<T> =
    try {
        val response = request()
        ApiResult.Success(
            data = response.body<T>(),
            expireDate = response.expireDate(),
            eTag = response.etag().orEmpty()
        )
    } catch (e: RedirectResponseException) {
        ApiResult.RedirectException
    } catch (e: Exception) {
        Log.e(PokeApiTag, e.localizedMessage, e)
        ApiResult.NoDataAvailable
    }

private fun HttpResponse.expireDate(): Long {
    val maxAge = this.headers[ApiHeadersKeys.CACHE_CONTROL]
        ?.substringAfter("max-age=")
        ?.substringBefore(",")
        ?.toLong() ?: ApiConstants.DEFAULT_REQUEST_MAX_AGE
    val maxAgeMilliseconds = maxAge * 1000

    return System.currentTimeMillis() + maxAgeMilliseconds
}