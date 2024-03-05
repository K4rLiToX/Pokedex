package com.carlosdiestro.core.pokedex

import com.carlosdiestro.core.pokedex.data.PokedexLocalDatasource
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID

class PokedexLocalDatasourceFake : PokedexLocalDatasource {

    private var database: MutableList<Pokedex> = mutableListOf()

    override suspend fun upsert(pokedexes: List<Pokedex>) {
        database.addAll(pokedexes)
    }

    override suspend fun getRegionPokedexes(regionId: RegionID): List<Pokedex> = database.toList()
}