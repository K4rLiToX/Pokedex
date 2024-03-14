package com.carlosdiestro.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexEntity
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.region.RegionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegionDaoTest {

    private lateinit var database: PokedexDatabase
    private lateinit var regionDao: RegionDao
    private lateinit var pokedexDao: PokedexDao

    @Before
    fun setUp() {
        database = Room
            .inMemoryDatabaseBuilder(
                context = InstrumentationRegistry.getInstrumentation().context,
                klass = PokedexDatabase::class.java,
            ).allowMainThreadQueries().build()
        regionDao = database.regionDao()
        pokedexDao = database.pokedexDao()
    }

    @Test
    fun `given an empty database when querying regions then return empty list`() = runBlocking {
        // given
        // Database is already empty when initialized

        // when
        val regions = regionDao.getAll().first()

        // then
        assertTrue(regions.isEmpty())
    }

    @Test
    fun `given an empty database when querying for a region then return null`() = runBlocking {
        // given
        // Database is already empty when initialized

        // when
        val region = regionDao.getRegion(1).first()

        // then
        assertTrue(region == null)
    }

    @Test
    fun `given an empty database when inserting a list of regions and querying them then return regions inserted`() =
        runBlocking {
            // given
            // Database is already empty when initialized

            // when
            val newRegions = listOf(
                RegionEntity(
                    id = 1,
                    name = "Kanto"
                ),
                RegionEntity(
                    id = 2,
                    name = "Johto"
                ),
                RegionEntity(
                    id = 3,
                    name = "Hoenn"
                )
            )

            regionDao.upsert(newRegions)
            val regions = regionDao.getAll().first()

            // then
            assertEquals(
                regions,
                newRegions
            )
        }

    @Test
    fun `given an empty database when inserting a region and querying it then return region inserted`() =
        runBlocking {
            // given
            // Database is already empty when initialize

            // when
            val regionId = 1
            val newRegion = RegionEntity(
                id = regionId,
                name = "Kanto"
            )

            regionDao.upsert(newRegion)
            val region = regionDao.getRegion(regionId).first()

            // then
            assertTrue(region?.region?.id == regionId)
        }

    @Test
    fun `given an empty database when inserting a region with pokedexes and querying it then return region with pokedexes inserted`() =
        runBlocking {
            // given
            // Database is already empty when initialized

            // when
            val regionId = 1
            val newRegion = RegionEntity(
                id = regionId,
                name = "Kanto"
            )
            val pokedexes = listOf(
                PokedexEntity(
                    id = 1,
                    regionId = regionId,
                    name = "Original"
                ),
                PokedexEntity(
                    id = 2,
                    regionId = regionId,
                    name = "Updated"
                )
            )
            regionDao.upsert(newRegion)
            pokedexDao.upsert(pokedexes)

            val region = regionDao.getRegion(regionId).first()

            // then
            assertTrue(region?.region?.id == regionId)
            assertTrue(region?.pokedexes == pokedexes)
        }
}