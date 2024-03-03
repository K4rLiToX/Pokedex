package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.core.region.domain.Region

class RegionRemoteDatasourceFake : RegionRemoteDatasource {

    private var returnFailure: Boolean = false

    fun makeFunctionReturnFailure() {
        returnFailure = true
    }

    override suspend fun getPokemonRegions(): Result<List<Region>> {
        return if (returnFailure) {
            Result.failure(Throwable())
        } else {
            Result.success(RegionsData.regions)
        }
    }
}

