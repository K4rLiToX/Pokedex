package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region

interface RegionRemoteDatasource {
    suspend fun getPokemonRegions(): Result<List<Region>>
}