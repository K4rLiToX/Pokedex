package com.carlosdiestro.database.pokemon

data class PokemonWithOrder(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val order: Int,
)