package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.SyncResult
import com.carlosdiestro.core.common.models.asSyncResult
import com.carlosdiestro.core.common.requests.RequestMetadata
import com.carlosdiestro.core.common.requests.RequestRepository
import com.carlosdiestro.core.common.requests.Route
import com.carlosdiestro.core.pokedex.domain.PokedexRepository
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val local: PokedexLocalDatasource,
    private val remote: PokedexRemoteDatasource,
    private val requestRepository: RequestRepository,
) : PokedexRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observePokedexEntries(pokedexId: ID): Flow<List<SimplePokemon>> = local.observePokedexEntries(pokedexId)

    override suspend fun synchronizePokemonEntries(pokedexId: ID): SyncResult {
        val requestRoute = Route("pokedex_${pokedexId.id}")
        val requestMetadata = requestRepository.getRequest(requestRoute)

        return scope.async {
            if (requestMetadata == null) {
                remote.getPokedexEntries(pokedexId.id)
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
                    remote.getPokedexEntries(pokedexId.id, requestMetadata.eTag.eTag)
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