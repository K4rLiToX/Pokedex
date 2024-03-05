package com.carlosdiestro.network.region.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)