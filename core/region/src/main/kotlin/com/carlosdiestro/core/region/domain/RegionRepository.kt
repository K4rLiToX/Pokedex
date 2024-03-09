package com.carlosdiestro.core.region.domain

import com.carlosdiestro.core.region.data.DataResource
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getPokemonRegions(): Flow<DataResource<List<SimpleRegion>>>
    fun getPokemonRegion(regionId: ID): Flow<DataResource<Region?>>
}