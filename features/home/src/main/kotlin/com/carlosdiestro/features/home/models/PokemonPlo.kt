package com.carlosdiestro.features.home.models

data class PokemonPlo(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val order: Int,
) {

    val curatedOrder: String
        get() = "#$order"
}
