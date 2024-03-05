package com.carlosdiestro.database.pokedex

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface PokedexDao {

    @Upsert
    suspend fun upsert(entities: List<PokedexEntity>)

    @Query(
        """
            SELECT *
            FROM pokedexes_table
            WHERE regionId = :regionId
        """
    )
    suspend fun getPokedexesByRegion(regionId: Int): List<PokedexEntity>
}