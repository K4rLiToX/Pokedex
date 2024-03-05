package com.carlosdiestro.network

private const val BaseUrl = "https://pokeapi.co/api/v2/"

internal object ApiRoutes {
    const val Regions = "$BaseUrl/region"
    const val Region = "$Regions/{${ApiUrlParameters.RegionId}}"
    const val Pokedex = "$BaseUrl/pokedex/"
    const val Pokemon = "$BaseUrl/pokemon/"
    const val PokemonSpecies = "$BaseUrl/pokemon-species/"
}

internal object ApiUrlParameters {
    const val RegionId: String = "regionId"
}