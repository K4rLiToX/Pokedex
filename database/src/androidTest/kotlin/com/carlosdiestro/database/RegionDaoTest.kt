package com.carlosdiestro.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.region.RegionEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegionDaoTest {

    private lateinit var database: PokedexDatabase
    private lateinit var dao: RegionDao

    @Before
    fun setUp() {
        database = Room
            .inMemoryDatabaseBuilder(
                context = InstrumentationRegistry.getInstrumentation().context,
                klass = PokedexDatabase::class.java,
            ).allowMainThreadQueries().build()
        dao = database.regionDao()
    }

    @Test
    fun `given an empty database when querying regions then return empty list`() = runBlocking {
        // given
        // Database is already empty when initialized

        // when
        val regions = dao.getAll()

        // then
        assertTrue(regions.isEmpty())
    }

    @Test
    fun `given an already cached database when querying regions then return regions inserted`() =
        runBlocking {
            // given
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

            dao.upsert(newRegions)

            // when
            val regions = dao.getAll()

            // then
            assertEquals(regions, newRegions)
        }
}