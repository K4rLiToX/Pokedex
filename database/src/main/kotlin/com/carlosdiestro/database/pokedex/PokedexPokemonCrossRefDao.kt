package com.carlosdiestro.database.pokedex

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface PokedexPokemonCrossRefDao {

    @Upsert
    suspend fun upsert(crossRefs: List<PokedexPokemonCrossRef>)
}