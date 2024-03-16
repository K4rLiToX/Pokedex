package com.carlosdiestro.core.common.models

import com.carlosdiestro.core.common.requests.ETag
import com.carlosdiestro.core.common.requests.ExpireDate

enum class SyncResult {
    Success,
    NotNecessary,
    Error
}

suspend inline fun <T> SyncState<T>.asSyncResult(
    crossinline cache: suspend (T) -> Unit,
    crossinline updateRequestMetadata: suspend (ExpireDate, ETag) -> Unit,
): SyncResult {
    return when (this) {
        is SyncState.Success   -> {
            cache(this.data)
            updateRequestMetadata(
                ExpireDate(this.expireDate),
                ETag(this.eTag)
            )
            SyncResult.Success
        }

        SyncState.NotModified  -> SyncResult.NotNecessary
        SyncState.NotAvailable -> SyncResult.Error
    }
}