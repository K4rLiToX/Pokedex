package com.carlosdiestro.network.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonSpeciesDto(
    @SerialName("egg_groups")
    val eggGroups: List<EggGroupDto>,
    @SerialName("evolution_chain")
    val evolutionChain: SimpleEvolutionChainDto,
)

@Serializable
internal data class EggGroupDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)

@Serializable
internal data class SimpleEvolutionChainDto(
    @SerialName("url")
    val url: String,
)
