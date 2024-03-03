package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource,
    private val local: RegionLocalDatasource
) : RegionRepository {
    
    override suspend fun getPokemonRegions(): Result<List<Region>> {
        val cacheRegions = local.getAll()
        val isCacheEmpty = cacheRegions.isEmpty()

        if (!isCacheEmpty) return Result.success(cacheRegions)

        return remote.getPokemonRegions().fold(
            onSuccess = { regions ->
                local.upsert(regions)
                Result.success(regions)
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}