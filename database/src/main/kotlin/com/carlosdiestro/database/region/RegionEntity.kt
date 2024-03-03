package com.carlosdiestro.database.region

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions_table")
data class RegionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)