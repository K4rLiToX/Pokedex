package com.carlosdiestro.core.region.domain

import com.carlosdiestro.core.region.data.SyncResult
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun observePokemonRegions(): Flow<List<SimpleRegion>>
    fun observePokemonRegion(regionId: ID): Flow<Region?>
    suspend fun synchronizePokemonRegions(): SyncResult
    suspend fun synchronizePokemonRegion(regionId: ID): SyncResult
}