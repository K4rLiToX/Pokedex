package com.carlosdiestro.database.pokemon

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface PokemonDao {

    @Upsert
    suspend fun upsert(pokemons: List<PokemonEntity>)
}