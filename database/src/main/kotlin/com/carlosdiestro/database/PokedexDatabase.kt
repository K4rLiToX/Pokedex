package com.carlosdiestro.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.region.RegionEntity

@Database(
    entities = [
        RegionEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class PokedexDatabase : RoomDatabase() {
    abstract fun regionDao(): RegionDao
}