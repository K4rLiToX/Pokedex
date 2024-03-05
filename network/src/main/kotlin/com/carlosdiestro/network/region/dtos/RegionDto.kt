package com.carlosdiestro.network.region.dtos

import com.carlosdiestro.network.pokedex.dtos.SimplePokedexDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    @SerialName("id")
    val id: Int,
    @SerialName("locations")
    val locations: List<LocationDto>,
    @SerialName("main_generation")
    val mainGeneration: MainGenerationDto,
    @SerialName("name")
    val name: String,
    @SerialName("names")
    val names: List<NameDto>,
    @SerialName("pokedexes")
    val pokedexes: List<SimplePokedexDto>,
    @SerialName("version_groups")
    val versionGroups: List<VersionGroupDto>
)