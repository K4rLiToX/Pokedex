package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.SimplePokedex

data class RegionCache(
    val id: Int,
    val name: String,
    val pokedexes: List<SimplePokedex>,
    val lastAccessed: Long
)
