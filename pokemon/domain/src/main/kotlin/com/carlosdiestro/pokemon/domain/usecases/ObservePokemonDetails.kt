package com.carlosdiestro.pokemon.domain.usecases

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.PokemonRepository
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePokemonDetails @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

    operator fun invoke(id: PokemonId): Flow<Event<PokemonDetails>> = pokemonRepository.observePokemonDetails(id)
}