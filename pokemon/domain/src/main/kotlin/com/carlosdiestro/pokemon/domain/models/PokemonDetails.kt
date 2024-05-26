package com.carlosdiestro.pokemon.domain.models

data class PokemonDetails(
    val entry: PokemonEntry,
    val types: List<PokemonType>,
    val height: PokemonHeight,
    val weight: PokemonWeight,
    val eggGroups: List<PokemonEggGroup>,
    val abilities: List<PokemonAbility>,
)

@JvmInline
value class PokemonType(val value: String)

@JvmInline
value class PokemonHeight(val value: Int)

@JvmInline
value class PokemonWeight(val value: Int)

@JvmInline
value class PokemonEggGroup(val value: String)

@JvmInline
value class PokemonAbility(val value: String)