package com.carlosdiestro.network.pokedex.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonEntryDto(
    @SerialName("entry_number")
    val index: Int,
    @SerialName("pokemon_species")
    val pokemon: SimplePokemonDto,
)