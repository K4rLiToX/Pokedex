package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import kotlinx.coroutines.flow.Flow

interface PokedexLocalDatasource {

    fun observePokedexEntries(pokedexId: ID): Flow<List<SimplePokemon>>
    suspend fun upsert(pokedex: Pokedex)
}