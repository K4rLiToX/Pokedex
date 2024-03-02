package com.carlosdiestro.core.region.domain

interface RegionRepository {
    suspend fun getPokemonRegions(): Result<List<Region>>
}