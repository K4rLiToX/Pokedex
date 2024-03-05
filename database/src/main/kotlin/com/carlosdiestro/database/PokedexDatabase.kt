package com.carlosdiestro.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexEntity
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.region.RegionEntity

@Database(
    entities = [
        RegionEntity::class,
        PokedexEntity::class,
    ],
    version = 1,
    exportSchema = false
)
internal abstract class PokedexDatabase : RoomDatabase() {
    abstract fun regionDao(): RegionDao
    abstract fun pokedexDao(): PokedexDao
}