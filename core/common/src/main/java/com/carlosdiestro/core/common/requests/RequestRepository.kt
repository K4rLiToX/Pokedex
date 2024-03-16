package com.carlosdiestro.core.common.requests

import javax.inject.Inject

interface RequestRepository {

    suspend fun getRequest(route: Route): RequestMetadata?
    suspend fun upsert(requestMetadata: RequestMetadata)
}

class RequestRepositoryImpl @Inject constructor(
    private val local: RequestLocalDatasource,
) : RequestRepository {

    override suspend fun getRequest(route: Route): RequestMetadata? = local.getRequest(route)

    override suspend fun upsert(requestMetadata: RequestMetadata) = local.upsert(requestMetadata)
}