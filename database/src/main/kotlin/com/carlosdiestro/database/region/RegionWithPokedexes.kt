package com.carlosdiestro.database.region

import androidx.room.Embedded
import androidx.room.Relation
import com.carlosdiestro.database.pokedex.PokedexEntity

data class RegionWithPokedexes(
    @Embedded
    val region: RegionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "regionId"
    )
    val pokedexes: List<PokedexEntity>,
)
