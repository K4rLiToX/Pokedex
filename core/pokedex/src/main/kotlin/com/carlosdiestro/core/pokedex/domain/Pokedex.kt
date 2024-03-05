package com.carlosdiestro.core.pokedex.domain

data class Pokedex(
    val id: ID,
    val regionId: RegionID,
    val name: Name
)

@JvmInline
value class ID(val id: Int)

@JvmInline
value class Name(val name: String)

@JvmInline
value class RegionID(val regionId: Int)
