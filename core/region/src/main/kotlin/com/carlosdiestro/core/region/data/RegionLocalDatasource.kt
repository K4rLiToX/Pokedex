package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.Region
import kotlinx.coroutines.flow.Flow

interface RegionLocalDatasource {
    suspend fun upsert(regions: List<Region>)
    suspend fun getAll(): List<Region>
}