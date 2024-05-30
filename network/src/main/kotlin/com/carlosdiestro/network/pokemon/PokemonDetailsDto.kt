package com.carlosdiestro.network.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonDetailsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("order")
    val order: Int,
    @SerialName("abilities")
    val abilities: List<AbilityDto>,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("stats")
    val stats: List<StatDto>,
    @SerialName("types")
    val types: List<TypeDto>,
)

@Serializable
internal data class AbilityDto(
    @SerialName("ability")
    val ability: SimpleAbilityDto,
)

@Serializable
internal data class SimpleAbilityDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)

@Serializable
internal data class StatDto(
    @SerialName("base_stat")
    val statValue: Int,
    @SerialName("stat")
    val stat: SimpleStatDto,
)

@Serializable
internal data class SimpleStatDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)

@Serializable
internal data class TypeDto(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: SimpleTypeDto,
)

@Serializable
internal data class SimpleTypeDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)
