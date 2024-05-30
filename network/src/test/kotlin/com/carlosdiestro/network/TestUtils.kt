package com.carlosdiestro.network

import com.carlosdiestro.network.pokedex.PokemonEntryDto
import com.carlosdiestro.network.pokedex.SimplePokemonSpeciesDto
import com.carlosdiestro.network.pokemon.AbilityDto
import com.carlosdiestro.network.pokemon.EggGroupDto
import com.carlosdiestro.network.pokemon.PokemonDto
import com.carlosdiestro.network.pokemon.SimpleAbilityDto
import com.carlosdiestro.network.pokemon.SimpleStatDto
import com.carlosdiestro.network.pokemon.SimpleTypeDto
import com.carlosdiestro.network.pokemon.StatDto
import com.carlosdiestro.network.pokemon.TypeDto
import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.Event.DataNotAvailable
import com.carlosdiestro.pokemon.domain.Event.DataNotModified
import com.carlosdiestro.pokemon.domain.Event.Success
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert


internal const val pokemonId = 1

internal val pokemonEntries = listOf(
    PokemonEntryDto(
        order = 1,
        pokemonSpecies = SimplePokemonSpeciesDto(
            name = "",
            url = "/$pokemonId"
        )
    )
)

internal val pokemon = PokemonDto(
    id = pokemonId,
    name = "",
    order = 1,
    types = listOf(
        TypeDto(
            slot = 0,
            type = SimpleTypeDto(
                name = "",
                url = ""
            )
        )
    ),
    height = 0,
    weight = 0,
    eggGroups = listOf(
        EggGroupDto(
            name = "",
            url = ""
        )
    ),
    abilities = listOf(
        AbilityDto(
            ability = SimpleAbilityDto(
                name = "",
                url = ""
            )
        )
    ),
    stats = listOf(
        StatDto(
            statValue = 0,
            stat = SimpleStatDto(
                name = "",
                url = ""
            )
        )
    )
)

internal fun <T> createPokeApiWithResult(
    result: ApiResult<T>,
    apiFunction: suspend PokeApi.() -> ApiResult<T>,
): PokeApi {
    return mockk<PokeApi> {
        coEvery { apiFunction() } returns result
    }
}

internal fun <T> Event<T>.assertSuccess() {
    Assert.assertTrue(this is Success)
}

internal fun <T> Event<T>.assertSuccessAndEmpty() {
    this.assertSuccess()
    Assert.assertTrue(((this as Success).data as List<*>).isEmpty())
}

internal fun <T> Event<T>.assertSuccessAndNotEmpty() {
    this.assertSuccess()
    Assert.assertTrue(((this as Success).data as List<*>).isNotEmpty())
}

internal fun <T> Event<T>.assertDataNotModified() {
    Assert.assertTrue(this is DataNotModified)
}

internal fun <T> Event<T>.assertDataNotAvailable() {
    Assert.assertTrue(this is DataNotAvailable)
}