package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource
): RegionRepository {
    override suspend fun getPokemonRegions(): Result<List<Region>> = remote.getPokemonRegions()
}