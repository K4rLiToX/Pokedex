package com.carlosdiestro.database.region

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface RegionDao {

    @Upsert
    suspend fun upsert(entities: List<RegionEntity>)

    @Query(
        """
            SELECT *
            FROM regions_table
        """
    )
    suspend fun getAll(): List<RegionEntity>
}