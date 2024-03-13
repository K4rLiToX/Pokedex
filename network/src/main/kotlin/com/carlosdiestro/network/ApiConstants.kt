package com.carlosdiestro.network

internal object ApiRoutes {
    private const val BASE_URL = "https://pokeapi.co/api/v2"
    const val REGIONS = "$BASE_URL/region"
}

internal object ApiHeadersKeys {
    const val IF_NONE_MATCH = "If-None-Match"
    const val CACHE_CONTROL = "Cache-Control"
}

internal object ApiConstants {
    const val DEFAULT_REQUEST_MAX_AGE = 86400L
    const val TIME_OUT = 10_000
}