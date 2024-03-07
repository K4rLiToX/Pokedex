package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.core.region.domain.SimpleRegion

class RegionLocalDatasourceFake : RegionLocalDatasource {

    private var database: MutableList<SimpleRegion> = mutableListOf()

    override suspend fun upsert(regions: List<SimpleRegion>) {
        database.addAll(regions)
    }

    override suspend fun getAll(): List<SimpleRegion> = database.toList()
}