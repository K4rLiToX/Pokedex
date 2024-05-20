package com.carlosdiestro.network

import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.statement.HttpResponse

internal sealed interface ApiResult<out T> {
    data class Success<T>(
        val data: T,
    ) : ApiResult<T>

    data object RedirectException : ApiResult<Nothing>

    data object DataNotAvailableException : ApiResult<Nothing>
}

internal suspend inline fun <reified T> suspendRunCatching(
    request: () -> HttpResponse,
): ApiResult<T> =
    try {
        val response = request()
        ApiResult.Success(data = response.body<T>())
    } catch (e: RedirectResponseException) {
        ApiResult.RedirectException
    } catch (e: Exception) {
        ApiResult.DataNotAvailableException
    }