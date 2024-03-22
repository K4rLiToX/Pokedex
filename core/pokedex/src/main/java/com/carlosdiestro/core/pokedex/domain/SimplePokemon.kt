package com.carlosdiestro.core.pokedex.domain

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name

data class SimplePokemon(
    val id: ID,
    val order: PokedexOrder,
    val name: Name,
    val spriteUrl: Url,
)

@JvmInline
value class PokedexOrder(val order: Int)

@JvmInline
value class Url(val url: String)