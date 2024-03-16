package com.carlosdiestro.network.pokedex.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplePokemonDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)