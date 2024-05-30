package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEntry

interface PokemonRemoteDatasource {

    suspend fun fetchPokemons(): Event<List<PokemonEntry>>

    suspend fun fetchPokemon(id: Int): Event<PokemonDetails>
}