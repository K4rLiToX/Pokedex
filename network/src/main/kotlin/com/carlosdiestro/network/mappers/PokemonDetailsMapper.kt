package com.carlosdiestro.network.mappers

import com.carlosdiestro.network.ApiRoutes
import com.carlosdiestro.network.pokemon.AbilityDto
import com.carlosdiestro.network.pokemon.EggGroupDto
import com.carlosdiestro.network.pokemon.PokemonDto
import com.carlosdiestro.network.pokemon.StatDto
import com.carlosdiestro.network.pokemon.TypeDto
import com.carlosdiestro.pokemon.domain.models.PokemonAbility
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEggGroup
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonHeight
import com.carlosdiestro.pokemon.domain.models.PokemonId
import com.carlosdiestro.pokemon.domain.models.PokemonName
import com.carlosdiestro.pokemon.domain.models.PokemonOrder
import com.carlosdiestro.pokemon.domain.models.PokemonStat
import com.carlosdiestro.pokemon.domain.models.PokemonType
import com.carlosdiestro.pokemon.domain.models.PokemonWeight
import com.carlosdiestro.pokemon.domain.models.SpriteUrl

internal fun PokemonDto.asDomain(): PokemonDetails = PokemonDetails(
    entry = this.getPokemonEntry(),
    types = this.types.asDomain(),
    height = PokemonHeight(this.height),
    weight = PokemonWeight(this.weight),
    eggGroups = this.eggGroups.asDomain(),
    abilities = this.abilities.asDomain(),
    stats = this.stats.asDomain()
)

internal fun PokemonDto.getPokemonEntry(): PokemonEntry = PokemonEntry(
    id = PokemonId(this.id),
    name = PokemonName(this.name),
    order = PokemonOrder(this.order),
    spriteUrl = SpriteUrl(id.getSpriteUrl())
)

private fun Int.getSpriteUrl(): String = "${ApiRoutes.BASE_POKEMON_SPRITE_URL}/${this}.png"

private fun List<TypeDto>.asDomain(): List<PokemonType> = this.map(TypeDto::asDomain)
private fun TypeDto.asDomain(): PokemonType = PokemonType(this.type.name)

private fun List<EggGroupDto>.asDomain(): List<PokemonEggGroup> = this.map(EggGroupDto::asDomain)
private fun EggGroupDto.asDomain(): PokemonEggGroup = PokemonEggGroup(this.name)

private fun List<AbilityDto>.asDomain(): List<PokemonAbility> = this.map(AbilityDto::asDomain)
private fun AbilityDto.asDomain(): PokemonAbility = PokemonAbility(this.ability.name)

private fun List<StatDto>.asDomain(): List<PokemonStat> = this.map(StatDto::asDomain)
private fun StatDto.asDomain(): PokemonStat = PokemonStat(
    name = this.stat.name,
    value = this.statValue
)