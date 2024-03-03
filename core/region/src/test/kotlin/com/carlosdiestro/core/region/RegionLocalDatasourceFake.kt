package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.core.region.domain.Region

class RegionLocalDatasourceFake : RegionLocalDatasource {

    private var database: MutableList<Region> = mutableListOf()

    override suspend fun upsert(regions: List<Region>) {
        database.addAll(regions)
    }

    override suspend fun getAll(): List<Region> = database.toList()
}