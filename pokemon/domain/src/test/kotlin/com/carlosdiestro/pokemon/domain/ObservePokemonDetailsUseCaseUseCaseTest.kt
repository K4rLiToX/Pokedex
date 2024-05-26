package com.carlosdiestro.pokemon.domain

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
import com.carlosdiestro.pokemon.domain.usecases.ObservePokemonDetailsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ObservePokemonDetailsUseCaseUseCaseTest {

    private val pokemonId = PokemonId(1)
    
    private val pokemonDetails = PokemonDetails(
        entry = PokemonEntry(
            id = pokemonId,
            name = PokemonName(""),
            order = PokemonOrder(0),
            spriteUrl = SpriteUrl("")
        ),
        height = PokemonHeight(0),
        weight = PokemonWeight(0),
        types = emptyList(),
        abilities = emptyList(),
        eggGroups = emptyList()
    )

    @Test
    fun observePokemonDetails_emitsValue_whenRepositoryReturnsValue() = runTest {
        val repository = createRepositoryWithEvent(
            event = Success(pokemonDetails),
            pokemonRepositoryFunction = {
                observePokemonDetails(pokemonId)
            }
        )

        val useCase = ObservePokemonDetailsUseCase(repository)

        useCase.assertSuccess()
    }

    @Test
    fun observePokemonDetails_emitsDataNotAvailable_whenRepositoryReturnsDataNotAvailable() = runTest {
        val repository = createRepositoryWithEvent(
            event = DataNotAvailable,
            pokemonRepositoryFunction = {
                observePokemonDetails(pokemonId)
            }
        )

        val useCase = ObservePokemonDetailsUseCase(repository)

        useCase.assertDataNotAvailable()
    }

    @Test
    fun observePokemonDetails_emitsDataNotModifier_whenRepositoryReturnsDataNotModified() = runTest {
        val repository = createRepositoryWithEvent(
            event = DataNotModified,
            pokemonRepositoryFunction = {
                observePokemonDetails(pokemonId)
            }
        )

        val useCase = ObservePokemonDetailsUseCase(repository)

        useCase.assertDataNotModified()
    }

    private suspend fun ObservePokemonDetailsUseCase.assertSuccess() {
        this.invoke(pokemonId).collect { event ->
            assertTrue(event is Success)
        }
    }

    private suspend fun ObservePokemonDetailsUseCase.assertDataNotAvailable() {
        this.invoke(pokemonId).collect { event ->
            assertTrue(event is DataNotAvailable)
        }
    }

    private suspend fun ObservePokemonDetailsUseCase.assertDataNotModified() {
        this.invoke(pokemonId).collect { event ->
            assertTrue(event is DataNotModified)
        }
    }
}