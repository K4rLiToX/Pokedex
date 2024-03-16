package com.carlosdiestro.core.pokedex.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.SyncResult
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun observePokedexEntries(pokedexId: ID): Flow<List<SimplePokemon>>
    suspend fun synchronizePokemonEntries(pokedexId: ID): SyncResult
}