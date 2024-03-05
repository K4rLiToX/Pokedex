package com.carlosdiestro.database.pokedex

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.carlosdiestro.database.region.RegionEntity

@Entity(
    tableName = "pokedexes_table",
    foreignKeys = [
        ForeignKey(
            entity = RegionEntity::class,
            parentColumns = ["id"],
            childColumns = ["regionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokedexEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val regionId: Int,
    val name: String
)