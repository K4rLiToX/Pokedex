package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID

interface PokedexLocalDatasource {
    suspend fun upsert(pokedexes: List<Pokedex>)
    suspend fun getRegionPokedexes(regionId: RegionID): List<Pokedex>
}