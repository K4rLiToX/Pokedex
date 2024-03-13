package com.carlosdiestro.core.region.common

interface RequestLocalDatasource {
    suspend fun upsert(requestMetadata: RequestMetadata)
    suspend fun getRequest(route: Route): RequestMetadata?
}