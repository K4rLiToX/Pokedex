package com.carlosdiestro.network.region.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleRegionDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)