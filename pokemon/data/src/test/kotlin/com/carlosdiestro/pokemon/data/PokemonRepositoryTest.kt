package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.Event.DataNotAvailable
import com.carlosdiestro.pokemon.domain.Event.DataNotModified
import com.carlosdiestro.pokemon.domain.Event.Success
import com.carlosdiestro.pokemon.domain.models.PokemonId
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class PokemonRepositoryTest {

    @Test
    fun observePokemonEntries_emitsEmptyList_whenDatasourceEmitsEmptyList() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = Success(emptyList()),
            datasourceFunction = { fetchPokemons() }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonEntries()

        flow.assertSuccessAndEmpty()
    }

    @Test
    fun observePokemonEntries_emitsValidList_whenDatasourceEmitsValidList() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = Success(listOf(mockk())),
            datasourceFunction = { fetchPokemons() }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonEntries()

        flow.assertSuccessAndNotEmpty()
    }

    @Test
    fun observePokemonEntries_emitsDataNotModified_whenDatasourceEmitsDataNotModifier() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = DataNotModified,
            datasourceFunction = { fetchPokemons() }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonEntries()

        flow.assertDataNotModified()
    }

    @Test
    fun observePokemonEntries_emitsDataNotAvailable_whenDatasourceEmitsDataNotAvailable() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = DataNotAvailable,
            datasourceFunction = { fetchPokemons() }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonEntries()

        flow.assertDataNotAvailable()
    }

    @Test
    fun observePokemonDetails_emitsValidDetails_whenDatasourceEmitsValidDetails() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = Success(pokemon),
            datasourceFunction = { fetchPokemon(pokemonId) }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonDetails(PokemonId(pokemonId))

        flow.assertSuccess()
    }

    @Test
    fun observePokemonDetails_emitsDataNotModifier_whenDatasourceEmitsDataNotModifier() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = DataNotModified,
            datasourceFunction = { fetchPokemon(pokemonId) }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonDetails(PokemonId(pokemonId))

        flow.assertDataNotModified()
    }

    @Test
    fun observePokemonDetails_emitsDataNotAvailable_whenDatasourceEmitsDataNotAvailable() = runTest {
        val datasource = createPokemonRemoteDatasourceWithEvent(
            event = DataNotAvailable,
            datasourceFunction = { fetchPokemon(pokemonId) }
        )

        val repository = PokemonRepositoryImpl(datasource)

        val flow = repository.observePokemonDetails(PokemonId(pokemonId))

        flow.assertDataNotAvailable()
    }
}