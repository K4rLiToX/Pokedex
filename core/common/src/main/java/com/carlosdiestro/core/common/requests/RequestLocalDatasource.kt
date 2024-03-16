package com.carlosdiestro.core.common.requests

interface RequestLocalDatasource {

    suspend fun upsert(requestMetadata: RequestMetadata)
    suspend fun getRequest(route: Route): RequestMetadata?
}