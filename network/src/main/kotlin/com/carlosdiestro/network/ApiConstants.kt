package com.carlosdiestro.network

internal object ApiConstants {
    const val TIME_OUT = 10_000
}

internal object ApiRoutes {
    private const val BASE_URL = "https://pokeapi.co/api/v2"
    const val NATIONAL_DEX_ENTRIES = "$BASE_URL/pokedex/1"
    const val POKEMON_SPECIES = "$BASE_URL/pokemon-species"
    const val POKEMON_DETAILS = "$BASE_URL/pokemon"
}