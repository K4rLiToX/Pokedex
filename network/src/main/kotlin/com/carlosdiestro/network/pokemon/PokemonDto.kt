package com.carlosdiestro.network.pokemon

internal data class PokemonDto(
    val types: List<TypeDto>,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilityDto>,
    val eggGroups: List<EggGroupDto>,
)
