package com.carlosdiestro.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokedexDaoTest {

    private lateinit var database: PokedexDatabase
    private lateinit var dao: PokedexDao

    @Before
    fun setUp() {
        database = Room
            .inMemoryDatabaseBuilder(
                context = InstrumentationRegistry.getInstrumentation().context,
                klass = PokedexDatabase::class.java
            ).allowMainThreadQueries().build()
        dao = database.pokedexDao()
    }

    @Test
    fun `given an empty database when querying region pokedexes then return empty list`() =
        runBlocking {
            // given
            // Database is already empty when initialized

            // when
            val regionId = 1
            val pokedexes = dao.getPokedexesByRegion(regionId)

            // then
            assertTrue(pokedexes.isEmpty())
        }

    @Test
    fun `given an already cached database when querying existant region pokedexes then return pokedexes inserted`() =
        runBlocking {
            // given
            val region1Id = 1
            val region2Id = 2
            val newPokedexes = listOf(
                PokedexEntity(
                    id = 2,
                    regionId = region1Id,
                    name = "Original"
                ),
                PokedexEntity(
                    id = 26,
                    regionId = region1Id,
                    name = "Lets Go"
                ),
                PokedexEntity(
                    id = 3,
                    regionId = region2Id,
                    name = "Original"
                ),
                PokedexEntity(
                    id = 4,
                    regionId = region2Id,
                    name = "Updated"
                )
            )

            dao.upsert(newPokedexes)

            // when
            val region1Pokedexes = dao.getPokedexesByRegion(region1Id)
            val region2Pokedexes = dao.getPokedexesByRegion(region2Id)

            // then
            assertEquals(region1Pokedexes, newPokedexes.filter { it.regionId == region1Id })
            assertEquals(region2Pokedexes, newPokedexes.filter { it.regionId == region2Id })
        }
}