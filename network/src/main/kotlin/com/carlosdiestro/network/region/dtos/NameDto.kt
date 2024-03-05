package com.carlosdiestro.network.region.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    @SerialName("language")
    val language: LanguageDto,
    @SerialName("name")
    val name: String
)