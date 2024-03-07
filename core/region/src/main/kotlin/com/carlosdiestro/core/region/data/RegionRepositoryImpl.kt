package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource,
    private val local: RegionLocalDatasource
) : RegionRepository {

    override fun getPokemonRegions(): Flow<Result<List<SimpleRegion>>> {
        return local.getAll().map { localRegions ->
            if (localRegions.isNotEmpty()) Result.success(localRegions)
            else {
                remote.getPokemonRegions().fold(
                    onSuccess = { remoteRegions ->
                        local.upsert(remoteRegions)
                        Result.success(remoteRegions)
                    },
                    onFailure = { e ->
                        Result.failure(e)
                    }
                )
            }
        }
    }

    override fun getPokemonRegion(regionId: ID): Flow<Result<Region>> {
        return local.getRegion(regionId.id).map { localRegion ->
            localRegion?.let {
                if (it.pokedexes.isNotEmpty()) Result.success(it)
                else cacheRemoteRegion(regionId.id)
            } ?: cacheRemoteRegion(regionId.id)
        }
    }

    private suspend fun cacheRemoteRegion(regionId: Int): Result<Region> {
        return remote.getPokemonRegion(regionId).fold(
            onSuccess = { remoteRegion ->
                local.upsert(remoteRegion)
                Result.success(remoteRegion)
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}