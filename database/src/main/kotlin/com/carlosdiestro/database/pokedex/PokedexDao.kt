package com.carlosdiestro.database.pokedex

import androidx.room.Dao
import androidx.room.Upsert

@Dao
internal interface PokedexDao {

    @Upsert
    suspend fun upsert(entities: List<PokedexEntity>)
}