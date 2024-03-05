package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.PokedexRepository
import com.carlosdiestro.core.pokedex.domain.RegionID
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val remote: PokedexRemoteDatasource,
    private val local: PokedexLocalDatasource
) : PokedexRepository {

    override suspend fun getRegionPokedexes(regionId: RegionID): Result<List<Pokedex>> {
        val cachePokedexes = local.getRegionPokedexes(regionId)
        val isCacheEmpty = cachePokedexes.isEmpty()

        if (!isCacheEmpty) return Result.success(cachePokedexes)

        return remote.getRegionPokedexes(regionId).fold(
            onSuccess = { pokedexes ->
                local.upsert(pokedexes)
                Result.success(pokedexes)
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}