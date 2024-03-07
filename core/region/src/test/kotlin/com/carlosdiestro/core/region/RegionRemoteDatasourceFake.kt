package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.core.region.domain.SimpleRegion

class RegionRemoteDatasourceFake : RegionRemoteDatasource {

    private var returnFailure: Boolean = false

    fun makeFunctionReturnFailure() {
        returnFailure = true
    }

    override suspend fun getPokemonRegions(): Result<List<SimpleRegion>> {
        return if (returnFailure) {
            Result.failure(Throwable())
        } else {
            Result.success(RegionsData.regions)
        }
    }
}

