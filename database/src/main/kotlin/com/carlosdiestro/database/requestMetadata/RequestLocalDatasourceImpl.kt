package com.carlosdiestro.database.requestMetadata

import com.carlosdiestro.core.common.requests.ETag
import com.carlosdiestro.core.common.requests.ExpireDate
import com.carlosdiestro.core.common.requests.RequestLocalDatasource
import com.carlosdiestro.core.common.requests.RequestMetadata
import com.carlosdiestro.core.common.requests.Route
import javax.inject.Inject

internal class RequestLocalDatasourceImpl @Inject constructor(
    private val dao: RequestMetadataDao,
) : com.carlosdiestro.core.common.requests.RequestLocalDatasource {

    override suspend fun upsert(requestMetadata: com.carlosdiestro.core.common.requests.RequestMetadata) =
        dao.upsert(requestMetadata.asEntity())

    override suspend fun getRequest(route: com.carlosdiestro.core.common.requests.Route): com.carlosdiestro.core.common.requests.RequestMetadata? =
        dao.getRequest(route.route)?.asDomain()
}

private fun com.carlosdiestro.core.common.requests.RequestMetadata.asEntity(): RequestMetadataEntity = RequestMetadataEntity(
    route = this.route.route,
    expireDate = this.expireDate.expireDate,
    eTag = this.eTag.eTag
)

private fun RequestMetadataEntity.asDomain(): com.carlosdiestro.core.common.requests.RequestMetadata =
    com.carlosdiestro.core.common.requests.RequestMetadata(
        route = com.carlosdiestro.core.common.requests.Route(this.route),
        expireDate = com.carlosdiestro.core.common.requests.ExpireDate(this.expireDate),
        eTag = com.carlosdiestro.core.common.requests.ETag(this.eTag)
    )