package com.carlosdiestro.pokemon.domain.usecases

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.PokemonRepository
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePokemonEntriesUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

    operator fun invoke(): Flow<Event<List<PokemonEntry>>> = pokemonRepository.observePokemonEntries()
}