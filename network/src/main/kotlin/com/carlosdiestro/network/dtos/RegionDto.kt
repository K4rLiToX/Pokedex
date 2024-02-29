package com.carlosdiestro.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)