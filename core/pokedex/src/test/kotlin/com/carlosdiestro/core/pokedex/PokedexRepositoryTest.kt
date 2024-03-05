package com.carlosdiestro.core.pokedex

import com.carlosdiestro.core.pokedex.data.PokedexRepositoryImpl
import com.carlosdiestro.core.pokedex.domain.PokedexRepository
import com.carlosdiestro.core.pokedex.domain.RegionID
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokedexRepositoryTest {

    private lateinit var repository: PokedexRepository
    private lateinit var remote: PokedexRemoteDatasourceFake
    private lateinit var local: PokedexLocalDatasourceFake

    @Before
    fun setUp() {
        remote = PokedexRemoteDatasourceFake()
        local = PokedexLocalDatasourceFake()
        repository = PokedexRepositoryImpl(
            remote = remote,
            local = local
        )
    }

    @Test
    fun `given an empty database, when fetching an existant region pokedexes, then cache and return data`() =
        runBlocking {
            // given
            // Database is already empty when initialized

            // when
            val regionId = RegionID(1)
            val result = repository.getRegionPokedexes(regionId)

            // then
            val localPokedexes = local.getRegionPokedexes(regionId)
            assertTrue(result.isSuccess)
            assertTrue(localPokedexes.isNotEmpty())
            assertTrue(localPokedexes.all { pokedex -> pokedex.regionId == regionId })
        }

    @Test
    fun `given an empty database, when fetching a non existant region pokedexes, then return failure`() =
        runBlocking {
            // given
            // Database is already empty when initialized

            // when
            val regionId = RegionID(-1)
            val result = repository.getRegionPokedexes(regionId)

            // then
            assertTrue(result.isFailure)
        }

    @Test
    fun `given an empty database and problems with the api, when fetching region pokedexes, then return failure`() =
        runBlocking {
            // given
            // Database is already empty when initialized
            remote.enableProblemsWithApi()

            // when
            val regionId = RegionID(1)
            val result = repository.getRegionPokedexes(regionId)

            // then
            assertTrue(result.isFailure)
        }

    @Test
    fun `given an already cached database, when fetching region pokedexes, then return cached data`() =
        runBlocking {
            // given
            val regionId = RegionID(1)
            local.upsert(PokedexData.getRegionPokedexes(regionId))

            // when
            val result = repository.getRegionPokedexes(regionId)

            // then
            assertTrue(result.isSuccess)
        }
}