package com.carlosdiestro.database.requestMetadata

import com.carlosdiestro.core.region.common.ETag
import com.carlosdiestro.core.region.common.ExpireDate
import com.carlosdiestro.core.region.common.RequestLocalDatasource
import com.carlosdiestro.core.region.common.RequestMetadata
import com.carlosdiestro.core.region.common.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RequestLocalDatasourceImpl @Inject constructor(
    private val dao: RequestMetadataDao,
) : RequestLocalDatasource {

    override suspend fun upsert(requestMetadata: RequestMetadata) =
        dao.upsert(
            withContext(Dispatchers.Default) { requestMetadata.asEntity() }
        )

    override suspend fun getRequest(route: Route): RequestMetadata? =
        dao.getRequest(route.route)?.let {
            withContext(Dispatchers.Default) { it.asDomain() }
        }
}

private fun RequestMetadata.asEntity(): RequestMetadataEntity = RequestMetadataEntity(
    route = this.route.route,
    expireDate = this.expireDate.expireDate,
    eTag = this.eTag.eTag
)

private fun RequestMetadataEntity.asDomain(): RequestMetadata = RequestMetadata(
    route = Route(this.route),
    expireDate = ExpireDate(this.expireDate),
    eTag = ETag(this.eTag)
)