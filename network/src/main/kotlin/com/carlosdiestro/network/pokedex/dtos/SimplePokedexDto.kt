package com.carlosdiestro.network.pokedex.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplePokedexDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)