package com.carlosdiestro.database.requestMetadata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "requests_metadata_table")
data class RequestMetadataEntity(
    @PrimaryKey(autoGenerate = false)
    val route: String,
    val expireDate: Long,
    val eTag: String,
)
