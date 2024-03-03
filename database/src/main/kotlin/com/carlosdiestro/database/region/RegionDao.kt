package com.carlosdiestro.database.region

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

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
    fun getAll(): Flow<List<RegionEntity>>
}