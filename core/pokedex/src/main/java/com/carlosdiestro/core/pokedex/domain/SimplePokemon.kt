package com.carlosdiestro.core.pokedex.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name

data class SimplePokemon(
    val id: ID,
    val index: PokedexIndex,
    val name: Name,
)

@JvmInline
value class PokedexIndex(val index: Int)