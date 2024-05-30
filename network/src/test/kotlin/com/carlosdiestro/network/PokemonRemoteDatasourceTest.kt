package com.carlosdiestro.network

import com.carlosdiestro.network.TestUtils.assertDataNotAvailable
import com.carlosdiestro.network.TestUtils.assertDataNotModified
import com.carlosdiestro.network.TestUtils.assertSuccess
import com.carlosdiestro.network.TestUtils.assertSuccessAndEmpty
import com.carlosdiestro.network.TestUtils.assertSuccessAndNotEmpty
import com.carlosdiestro.network.TestUtils.createPokeApiWithResult
import com.carlosdiestro.network.TestUtils.pokemon
import com.carlosdiestro.network.TestUtils.pokemonEntries
import com.carlosdiestro.network.TestUtils.pokemonId
import com.carlosdiestro.network.pokedex.PokedexDto
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class PokemonRemoteDatasourceTest {

    @Test
    fun fetchPokemons_emitsEmptyList_whenApiReturnsEmptyList() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.Success(PokedexDto(emptyList())),
            apiFunction = { fetchNationalDex() }
        )
        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemons()

        result.assertSuccessAndEmpty()
    }

    @Test
    fun fetchPokemons_emitsValidList_whenApiReturnsValidList() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.Success(PokedexDto(pokemonEntries)),
            apiFunction = { fetchNationalDex() }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemons()

        result.assertSuccessAndNotEmpty()
    }

    @Test
    fun fetchPokemons_emitsDataNotModified_whenApiReturnsDataNotModified() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.RedirectException,
            apiFunction = { fetchNationalDex() }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemons()

        result.assertDataNotModified()
    }

    @Test
    fun fetchPokemons_emitsDataNotAvailable_whenApiReturnsDataNotAvailable() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.DataNotAvailableException,
            apiFunction = { fetchNationalDex() }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemons()

        result.assertDataNotAvailable()
    }

    @Test
    fun fetchPokemon_emitsValue_whenApiReturnsValue() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.Success(pokemon),
            apiFunction = { fetchPokemon(pokemonId) }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemon(pokemonId)

        result.assertSuccess()
    }

    @Test
    fun fetchPokemon_emitsDataNotAvailable_whenApiReturnsDataNotAvailable() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.DataNotAvailableException,
            apiFunction = { fetchPokemon(pokemonId) }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemon(pokemonId)

        result.assertDataNotAvailable()
    }

    @Test
    fun fetchPokemon_emitsDataNotModified_whenApiReturnsDataNotModified() = runTest {
        val api = createPokeApiWithResult(
            result = ApiResult.RedirectException,
            apiFunction = { fetchPokemon(pokemonId) }
        )

        val datasource = PokemonRemoteDatasourceImpl(api)

        val result = datasource.fetchPokemon(pokemonId)

        result.assertDataNotModified()
    }
}