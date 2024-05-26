package com.carlosdiestro.pokemon.domain

import com.carlosdiestro.pokemon.domain.Event.DataNotAvailable
import com.carlosdiestro.pokemon.domain.Event.DataNotModified
import com.carlosdiestro.pokemon.domain.Event.Success
import com.carlosdiestro.pokemon.domain.usecases.ObservePokemonEntriesUseCase
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ObservePokemonEntriesUseCaseTest {

    @Test
    fun observePokemonEntries_emitsEmptyList_whenRepositoryReturnsEmptyList() = runTest {
        val repository = createRepositoryWithEvent(
            event = Success(emptyList()),
            pokemonRepositoryFunction = {
                observePokemonEntries()
            }
        )

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertSuccessAndEmpty()
    }

    @Test
    fun observePokemonEntries_emitsValidList_whenRepositoryReturnsValidList() = runTest {
        val repository = createRepositoryWithEvent(
            event = Success(listOf(mockk())),
            pokemonRepositoryFunction = {
                observePokemonEntries()
            }
        )

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertSuccessAndNotEmpty()
    }

    @Test
    fun observePokemonEntries_emitsDataNotModified_whenRepositoryReturnsDataNotModified() = runTest {
        val repository = createRepositoryWithEvent(
            event = DataNotModified,
            pokemonRepositoryFunction = {
                observePokemonEntries()
            }
        )

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertDataNotModified()
    }

    @Test
    fun observePokemonEntries_emitsDataNotAvailable_whenRepositoryReturnsDataNotAvailable() = runTest {
        val repository = createRepositoryWithEvent(
            event = DataNotAvailable,
            pokemonRepositoryFunction = {
                observePokemonEntries()
            }
        )

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertDataNotAvailable()
    }

    private suspend fun ObservePokemonEntriesUseCase.assertSuccessAndEmpty() {
        this.invoke().collect { event ->
            assertTrue(event is Success)
            assertTrue((event as Success).data.isEmpty())
        }
    }

    private suspend fun ObservePokemonEntriesUseCase.assertSuccessAndNotEmpty() {
        this.invoke().collect { event ->
            assertTrue(event is Success)
            assertTrue((event as Success).data.isNotEmpty())
        }
    }

    private suspend fun ObservePokemonEntriesUseCase.assertDataNotModified() {
        this.invoke().collect { event ->
            assertTrue(event is DataNotModified)
        }
    }

    private suspend fun ObservePokemonEntriesUseCase.assertDataNotAvailable() {
        this.invoke().collect { event ->
            assertTrue(event is DataNotAvailable)
        }
    }
}