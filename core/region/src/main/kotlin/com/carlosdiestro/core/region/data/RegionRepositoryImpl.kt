package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource,
    private val local: RegionLocalDatasource
): RegionRepository {
    override suspend fun getPokemonRegions(): Result<List<Region>> {
        val cachedRegions = local.getAll().first()
        val isCacheEmpty = cachedRegions.isEmpty()

        return if (isCacheEmpty) {
            remote.getPokemonRegions().fold(
                onSuccess = { regions ->
                    local.upsert(regions)
                    Result.success(regions)
                },
                onFailure = { e ->
                    Result.failure(e)
                }
            )
        } else {
            Result.success(cachedRegions)
        }
    }
}