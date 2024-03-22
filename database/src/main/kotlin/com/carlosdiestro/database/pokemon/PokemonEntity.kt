package com.carlosdiestro.database.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val spriteUrl: String,
)