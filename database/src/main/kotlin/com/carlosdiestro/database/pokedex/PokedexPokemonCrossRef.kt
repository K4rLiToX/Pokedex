package com.carlosdiestro.database.pokedex

import androidx.room.Entity

@Entity(
    tableName = "pokedex_pokemon_cross_ref",
    primaryKeys = ["pokedexId", "pokemonId"]
)
data class PokedexPokemonCrossRef(
    val pokedexId: Int,
    val pokemonId: Int,
    val order: Int,
)