package com.carlosdiestro.database.region

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RegionDao {

    @Upsert
    suspend fun upsert(entities: List<RegionEntity>)

    @Upsert
    suspend fun upsert(entity: RegionEntity)

    @Query(
        """
            SELECT *
            FROM regions_table
        """
    )
    fun getAll(): Flow<List<RegionEntity>>

    @Transaction
    @Query(
        """
            SELECT *
            FROM regions_table
            WHERE id == :regionId
        """
    )
    fun getRegion(regionId: Int): Flow<RegionWithPokedexes?>
}