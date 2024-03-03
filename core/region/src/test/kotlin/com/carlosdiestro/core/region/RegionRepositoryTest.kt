package com.carlosdiestro.core.region

import com.carlosdiestro.core.region.data.RegionRepositoryImpl
import com.carlosdiestro.core.region.domain.RegionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegionRepositoryTest {

    private lateinit var repository: RegionRepository
    private lateinit var remote: RegionRemoteDatasourceFake
    private lateinit var local: RegionLocalDatasourceFake

    @Before
    fun setUp() {
        remote = RegionRemoteDatasourceFake()
        local = RegionLocalDatasourceFake()
        repository = RegionRepositoryImpl(
            remote = remote,
            local = local
        )
    }

    @Test
    fun `given an empty database, when fetching pokemon regions, then cache and return data`() =
        runBlocking {
            // given
            // Database is always empty when initialized

            // when
            val result = repository.getPokemonRegions()

            // then
            assertTrue(result.isSuccess)
            assertTrue(local.getAll().isNotEmpty())
        }

    @Test
    fun `given an empty database and problems with the api, when fetching pokemon regions, then return failure`() =
        runBlocking {
            // given
            // Database is always empty when initialized
            remote.makeFunctionReturnFailure()

            // when
            val result = repository.getPokemonRegions()

            // then
            assertTrue(result.isFailure)
        }

    @Test
    fun `given an already cached database, when fetching pokemon regions, then return cached data`() =
        runBlocking {
            // given
            local.upsert(RegionsData.regions)

            // when
            val result = repository.getPokemonRegions()

            // then
            assertTrue(result.isSuccess)
        }
}