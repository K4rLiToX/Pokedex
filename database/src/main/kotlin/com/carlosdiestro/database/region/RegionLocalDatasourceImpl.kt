package com.carlosdiestro.database.region

import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.Region
import javax.inject.Inject

internal class RegionLocalDatasourceImpl @Inject constructor(
    private val dao: RegionDao
) : RegionLocalDatasource {

    override suspend fun upsert(regions: List<Region>) = dao.upsert(regions.asEntity())

    override suspend fun getAll(): List<Region> = dao.getAll().asDomain()
}

private fun List<RegionEntity>.asDomain(): List<Region> = this.map { it.asDomain() }

private fun RegionEntity.asDomain(): Region = Region(
    id = ID(this.id),
    name = Name(this.name)
)

private fun List<Region>.asEntity(): List<RegionEntity> = this.map { region -> region.asEntity() }

private fun Region.asEntity(): RegionEntity = RegionEntity(
    id = this.id.id,
    name = this.name.name
)