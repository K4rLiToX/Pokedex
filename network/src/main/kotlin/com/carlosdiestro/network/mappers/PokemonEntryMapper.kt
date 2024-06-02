package com.carlosdiestro.network.mappers

import android.net.Uri
import com.carlosdiestro.network.ApiRoutes
import com.carlosdiestro.network.pokedex.PokemonEntryDto
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonId
import com.carlosdiestro.pokemon.domain.models.PokemonName
import com.carlosdiestro.pokemon.domain.models.PokemonOrder
import com.carlosdiestro.pokemon.domain.models.SpriteUrl

internal fun List<PokemonEntryDto>.asDomain(): List<PokemonEntry> = this.map(PokemonEntryDto::asDomain)

internal fun PokemonEntryDto.asDomain(): PokemonEntry = PokemonEntry(
    id = PokemonId(pokemonSpecies.url.getPokemonId()),
    name = PokemonName(pokemonSpecies.name),
    order = PokemonOrder(order),
    spriteUrl = SpriteUrl(pokemonSpecies.url.getSpriteUrl())
)

private fun String.getPokemonId(): Int = Uri.parse(this).lastPathSegment?.toInt() ?: 1
private fun String.getSpriteUrl(): String = "${ApiRoutes.BASE_POKEMON_SPRITE_URL}/${this.getPokemonId()}.png"