package com.carlosdiestro.pokemon.domain.models

data class PokemonEntry(
    val id: PokemonId,
    val name: PokemonName,
    val order: PokemonOrder,
    val spriteUrl: SpriteUrl
)

@JvmInline
value class PokemonId(val value: Int)

@JvmInline
value class PokemonName(val value: String)

@JvmInline
value class PokemonOrder(val value: Int)

@JvmInline
value class SpriteUrl(val value: String)
