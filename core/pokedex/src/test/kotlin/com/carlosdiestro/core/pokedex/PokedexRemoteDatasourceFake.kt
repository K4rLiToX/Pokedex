package com.carlosdiestro.core.pokedex

import com.carlosdiestro.core.pokedex.data.PokedexRemoteDatasource
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID

class PokedexRemoteDatasourceFake : PokedexRemoteDatasource {

    private var enableProblemsWithApi: Boolean = false

    fun enableProblemsWithApi() {
        enableProblemsWithApi = true
    }

    override suspend fun getRegionPokedexes(regionId: RegionID): Result<List<Pokedex>> {
        if (enableProblemsWithApi) return Result.failure(Throwable())

        val pokedexes = PokedexData.getRegionPokedexes(regionId)

        if (pokedexes.isEmpty()) return Result.failure(Throwable())

        return Result.success(pokedexes)
    }
}