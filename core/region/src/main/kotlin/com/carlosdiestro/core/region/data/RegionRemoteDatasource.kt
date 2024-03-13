package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimpleRegion

interface RegionRemoteDatasource {
    suspend fun getPokemonRegions(eTag: String = ""): SyncState<List<SimpleRegion>>
    suspend fun getPokemonRegion(regionId: Int, eTag: String = ""): SyncState<Region>
}