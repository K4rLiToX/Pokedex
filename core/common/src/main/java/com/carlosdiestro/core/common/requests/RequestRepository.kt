package com.carlosdiestro.core.common.requests

interface RequestRepository {

    suspend fun getRequest(route: Route): RequestMetadata?
    suspend fun upsert(requestMetadata: RequestMetadata)
}