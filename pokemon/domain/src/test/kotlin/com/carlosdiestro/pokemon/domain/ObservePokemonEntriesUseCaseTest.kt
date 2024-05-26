package com.carlosdiestro.pokemon.domain

import com.carlosdiestro.pokemon.domain.Event.DataNotAvailable
import com.carlosdiestro.pokemon.domain.Event.DataNotModified
import com.carlosdiestro.pokemon.domain.Event.Success
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.usecases.ObservePokemonEntriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ObservePokemonEntriesUseCaseTest {

    @Test
    fun observePokemonEntries_emitsEmptyList_whenRepositoryReturnsEmptyList() = runTest {
        val repository = createRepositoryWithEvent(Success(emptyList()))

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertSuccessAndEmpty()
    }

    @Test
    fun observePokemonEntries_emitsValidList_whenRepositoryReturnsValidList() = runTest {
        val repository = createRepositoryWithEvent(Success(listOf(mockk())))

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertSuccessAndNotEmpty()
    }

    @Test
    fun observePokemonEntries_emitsDataNotModified_whenRepositoryReturnsDataNotModified() = runTest {
        val repository = createRepositoryWithEvent(DataNotModified)

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertDataNotModified()
    }

    @Test
    fun observePokemonEntries_emitsDataNotAvailable_whenRepositoryReturnsDataNotAvailable() = runTest {
        val repository = createRepositoryWithEvent(DataNotAvailable)

        val useCase = ObservePokemonEntriesUseCase(repository)

        useCase.assertDataNotAvailable()
    }

    private fun createRepositoryWithEvent(event: Event<List<PokemonEntry>>): PokemonRepository {
        return mockk<PokemonRepository> {
            coEvery { observePokemonEntries() } returns flowOf(event)
        }
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