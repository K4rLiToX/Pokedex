package com.carlosdiestro.pokemon.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal fun <T> createRepositoryWithEvent(event: Event<T>, pokemonRepositoryFunction: PokemonRepository.() -> Flow<Event<T>>): PokemonRepository {
    return mockk<PokemonRepository> {
        coEvery { pokemonRepositoryFunction() } returns flowOf(event)
    }
}