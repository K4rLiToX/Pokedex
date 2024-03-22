package com.carlosdiestro.core.pokedex.domain

import com.carlosdiestro.core.common.models.ID

data class Pokedex(
    val id: ID,
    val pokemons: List<SimplePokemon>,
)
