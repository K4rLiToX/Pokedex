package com.carlosdiestro.core.region.data

sealed interface SyncState<out T> {
    data class Success<T>(
        val data: T,
        val expireDate: Long,
        val eTag: String,
    ) : SyncState<T>

    data object NotModified : SyncState<Nothing>
    data object NotAvailable : SyncState<Nothing>
}