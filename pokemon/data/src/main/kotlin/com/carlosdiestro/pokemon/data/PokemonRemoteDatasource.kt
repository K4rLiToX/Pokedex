package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.models.PokemonEntry

interface PokemonRemoteDatasource {

    suspend fun fetchPokemons(): Event<List<PokemonEntry>>
}