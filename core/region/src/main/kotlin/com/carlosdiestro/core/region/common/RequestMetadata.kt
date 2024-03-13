package com.carlosdiestro.core.region.common

data class RequestMetadata(
    val route: Route,
    val expireDate: ExpireDate,
    val eTag: ETag
) {
    val isExpired: Boolean
        get() = expireDate.expireDate >= System.currentTimeMillis()
}

@JvmInline
value class Route(val route: String)

@JvmInline
value class ExpireDate(val expireDate: Long)

@JvmInline
value class ETag(val eTag: String)
