package com.carlosdiestro.network.pokedex.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokedexDto(
    @SerialName("pokemon_entries")
    val entries: List<PokemonEntryDto>,
)