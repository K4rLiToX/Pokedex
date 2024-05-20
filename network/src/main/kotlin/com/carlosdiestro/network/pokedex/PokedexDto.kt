package com.carlosdiestro.network.pokedex

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokedexDto(
    @SerialName("pokemon_entries")
    val pokemonEntries: List<PokemonEntryDto>,
)

@Serializable
internal data class PokemonEntryDto(
    @SerialName("entry_number")
    val order: Int,
    @SerialName("pokemon_species")
    val pokemonSpecies: SimplePokemonSpeciesDto,
)

@Serializable
internal data class SimplePokemonSpeciesDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)