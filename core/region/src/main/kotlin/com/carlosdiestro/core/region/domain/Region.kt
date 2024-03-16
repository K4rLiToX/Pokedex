package com.carlosdiestro.core.region.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name

data class Region(
    val id: ID,
    val name: Name,
    val pokedexes: List<SimplePokedex>,
)
