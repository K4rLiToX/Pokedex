package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID

interface PokedexRemoteDatasource {
    suspend fun getRegionPokedexes(regionId: RegionID): Result<List<Pokedex>>
}