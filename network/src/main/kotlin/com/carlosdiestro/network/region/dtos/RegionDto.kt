package com.carlosdiestro.network.region.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("pokedexes")
    val pokedexes: List<SimplePokedexDto>
)