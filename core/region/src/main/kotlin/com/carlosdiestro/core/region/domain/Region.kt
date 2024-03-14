package com.carlosdiestro.core.region.domain

data class Region(
    val id: ID,
    val name: Name,
    val pokedexes: List<SimplePokedex>,
)
