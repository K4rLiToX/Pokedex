package com.carlosdiestro.core.region.domain

import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getPokemonRegions(): Flow<Result<List<SimpleRegion>>>
    fun getPokemonRegion(regionId: ID): Flow<Result<Region>>
}