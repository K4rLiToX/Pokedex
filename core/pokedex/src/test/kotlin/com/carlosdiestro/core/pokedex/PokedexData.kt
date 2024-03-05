package com.carlosdiestro.core.pokedex

import com.carlosdiestro.core.pokedex.domain.ID
import com.carlosdiestro.core.pokedex.domain.Name
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID

object PokedexData {
    private val pokedexes: List<Pokedex> = listOf(
        Pokedex(
            id = ID(2),
            regionId = RegionID(1),
            name = Name("Original")
        ),
        Pokedex(
            id = ID(26),
            regionId = RegionID(1),
            name = Name("Lets Go")
        ),
        Pokedex(
            id = ID(3),
            regionId = RegionID(2),
            name = Name("Original")
        ),
        Pokedex(
            id = ID(7),
            regionId = RegionID(2),
            name = Name("Updated")
        )
    )

    fun getRegionPokedexes(regionId: RegionID): List<Pokedex> = pokedexes.filter { pokedex ->
        pokedex.regionId == regionId
    }
}