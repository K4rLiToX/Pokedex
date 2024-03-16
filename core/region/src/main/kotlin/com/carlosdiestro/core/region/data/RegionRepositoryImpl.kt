package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.SyncResult
import com.carlosdiestro.core.common.models.asSyncResult
import com.carlosdiestro.core.common.requests.RequestMetadata
import com.carlosdiestro.core.common.requests.RequestRepository
import com.carlosdiestro.core.common.requests.Route
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource,
    private val local: RegionLocalDatasource,
    private val requestRepository: RequestRepository,
) : RegionRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observePokemonRegions(): Flow<List<SimpleRegion>> = local.getAll()

    override fun observePokemonRegion(regionId: ID): Flow<Region?> = local.getRegion(regionId.id)

    override suspend fun synchronizePokemonRegions(): SyncResult {
        val requestRoute = Route("regions")
        val requestMetadata = requestRepository.getRequest(requestRoute)

        return scope.async {
            if (requestMetadata == null) {
                remote.getPokemonRegions()
                    .asSyncResult(
                        cache = local::upsert,
                        updateRequestMetadata = { expireDate, eTag ->
                            requestRepository.upsert(
                                RequestMetadata(
                                    route = requestRoute,
                                    expireDate = expireDate,
                                    eTag = eTag
                                )
                            )
                        }
                    )
            } else {
                if (!requestMetadata.isExpired) SyncResult.NotNecessary
                else {
                    remote.getPokemonRegions(requestMetadata.eTag.eTag)
                        .asSyncResult(
                            cache = local::upsert,
                            updateRequestMetadata = { expireDate, eTag ->
                                requestRepository.upsert(
                                    requestMetadata.copy(
                                        expireDate = expireDate,
                                        eTag = eTag
                                    )
                                )
                            }
                        )
                }
            }
        }.await()
    }

    override suspend fun synchronizePokemonRegion(regionId: ID): SyncResult {
        val requestRoute = Route("region_${regionId.id}")
        val requestMetadata = requestRepository.getRequest(requestRoute)

        return scope.async {
            if (requestMetadata == null) {
                remote.getPokemonRegion(regionId.id)
                    .asSyncResult(
                        cache = local::upsert,
                        updateRequestMetadata = { expireDate, eTag ->
                            requestRepository.upsert(
                                RequestMetadata(
                                    route = requestRoute,
                                    expireDate = expireDate,
                                    eTag = eTag
                                )
                            )
                        }
                    )
            } else {
                if (!requestMetadata.isExpired) SyncResult.NotNecessary
                else {
                    remote.getPokemonRegion(
                        regionId.id,
                        requestMetadata.eTag.eTag
                    )
                        .asSyncResult(
                            cache = local::upsert,
                            updateRequestMetadata = { expireDate, eTag ->
                                requestRepository.upsert(
                                    requestMetadata.copy(
                                        expireDate = expireDate,
                                        eTag = eTag
                                    )
                                )
                            }
                        )
                }
            }
        }.await()
    }
}