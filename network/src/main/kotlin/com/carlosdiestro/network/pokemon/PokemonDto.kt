package com.carlosdiestro.network.pokemon

internal data class PokemonDto(
    val id: Int,
    val name: String,
    val order: Int,
    val types: List<TypeDto>,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilityDto>,
    val eggGroups: List<EggGroupDto>,
    val stats: List<StatDto>,
)
