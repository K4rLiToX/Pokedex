package com.carlosdiestro.database.pokedex

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokedexes_table")
data class PokedexEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val regionId: Int,
    val name: String,
)