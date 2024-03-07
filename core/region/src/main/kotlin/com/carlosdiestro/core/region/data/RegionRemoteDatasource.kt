package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimpleRegion

interface RegionRemoteDatasource {
    suspend fun getPokemonRegions(): Result<List<SimpleRegion>>
    suspend fun getPokemonRegion(regionId: Int): Result<Region>
}