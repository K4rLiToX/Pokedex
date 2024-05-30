package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.Event.DataNotAvailable
import com.carlosdiestro.pokemon.domain.Event.DataNotModified
import com.carlosdiestro.pokemon.domain.Event.Success
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonHeight
import com.carlosdiestro.pokemon.domain.models.PokemonId
import com.carlosdiestro.pokemon.domain.models.PokemonName
import com.carlosdiestro.pokemon.domain.models.PokemonOrder
import com.carlosdiestro.pokemon.domain.models.PokemonWeight
import com.carlosdiestro.pokemon.domain.models.SpriteUrl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertTrue

internal const val pokemonId = 1

internal val pokemon = PokemonDetails(
    entry = PokemonEntry(
        id = PokemonId(pokemonId),
        name = PokemonName(""),
        order = PokemonOrder(1),
        spriteUrl = SpriteUrl("")
    ),
    types = emptyList(),
    height = PokemonHeight(0),
    weight = PokemonWeight(0),
    eggGroups = emptyList(),
    abilities = emptyList(),
    stats = emptyList()
)

internal fun <T> createPokemonRemoteDatasourceWithEvent(
    event: Event<T>,
    datasourceFunction: suspend PokemonRemoteDatasource.() -> Event<T>,
): PokemonRemoteDatasource {
    return mockk<PokemonRemoteDatasource> {
        coEvery { datasourceFunction() } returns event
    }
}

internal suspend fun <T> Flow<Event<T>>.assertSuccess() {
    this.collect { event ->
        event.assertSuccess()
    }
}

internal suspend fun <T> Flow<Event<T>>.assertSuccessAndEmpty() {
    this.collect { event ->
        event.assertSuccess()
        assertTrue(((event as Success).data as List<*>).isEmpty())
    }
}

internal suspend fun <T> Flow<Event<T>>.assertSuccessAndNotEmpty() {
    this.collect { event ->
        event.assertSuccess()
        assertTrue(((event as Success).data as List<*>).isNotEmpty())
    }
}

internal suspend fun <T> Flow<Event<T>>.assertDataNotModified() {
    this.collect { event ->
        assertTrue(event is DataNotModified)
    }
}

internal suspend fun <T> Flow<Event<T>>.assertDataNotAvailable() {
    this.collect { event ->
        assertTrue(event is DataNotAvailable)
    }
}

private fun <T> Event<T>.assertSuccess() {
    assertTrue(this is Success)
}