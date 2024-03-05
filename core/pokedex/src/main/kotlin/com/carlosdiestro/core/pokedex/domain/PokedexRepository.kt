package com.carlosdiestro.core.pokedex.domain

interface PokedexRepository {
    suspend fun getRegionPokedexes(regionId: RegionID): Result<List<Pokedex>>
}