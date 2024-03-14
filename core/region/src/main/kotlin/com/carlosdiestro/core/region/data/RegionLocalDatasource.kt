package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimpleRegion
import kotlinx.coroutines.flow.Flow

interface RegionLocalDatasource {

    suspend fun upsert(regions: List<SimpleRegion>)
    suspend fun upsert(region: Region)
    fun getAll(): Flow<List<SimpleRegion>>
    fun getRegion(regionId: Int): Flow<Region?>
}