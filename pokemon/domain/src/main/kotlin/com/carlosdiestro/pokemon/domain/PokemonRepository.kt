package com.carlosdiestro.pokemon.domain

import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonId
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun observePokemonEntries(): Flow<Event<List<PokemonEntry>>>
    fun observePokemonDetails(id: PokemonId): Flow<Event<PokemonDetails>>
}