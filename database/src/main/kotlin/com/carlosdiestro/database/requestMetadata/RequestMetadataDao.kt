package com.carlosdiestro.database.requestMetadata

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface RequestMetadataDao {

    @Upsert
    suspend fun upsert(entity: RequestMetadataEntity)

    @Query(
        """
            SELECT *
            FROM requests_metadata_table
            WHERE route = :route
        """
    )
    suspend fun getRequest(route: String): RequestMetadataEntity?
}