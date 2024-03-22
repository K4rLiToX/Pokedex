package com.carlosdiestro.database.region

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name
import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimplePokedex
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RegionLocalDatasourceImpl @Inject constructor(
    private val regionDao: RegionDao,
    private val pokedexDao: PokedexDao,
) : RegionLocalDatasource {

    override suspend fun upsert(regions: List<SimpleRegion>) = regionDao.upsert(regions.asEntity())

    override suspend fun upsert(region: Region) {
        regionDao.upsert(region.asEntity())
        pokedexDao.upsert(region.pokedexes.asEntity())
    }

    override fun getAll(): Flow<List<SimpleRegion>> =
        regionDao.getAll().map(List<RegionEntity>::asDomain)

    override fun getRegion(regionId: Int): Flow<Region?> =
        regionDao.getRegion(regionId).map { it?.asDomain() }
}

private fun List<RegionEntity>.asDomain(): List<SimpleRegion> =
    this.map(RegionEntity::asDomain)

private fun RegionEntity.asDomain(): SimpleRegion =
    SimpleRegion(
        id = ID(this.id),
        name = Name(this.name)
    )

private fun List<SimpleRegion>.asEntity(): List<RegionEntity> =
    this.map(SimpleRegion::asEntity)

private fun SimpleRegion.asEntity(): RegionEntity = RegionEntity(
    id = this.id.id,
    name = this.name.name.replaceFirstChar { it.uppercase() }
)

private fun Region.asEntity(): RegionEntity = RegionEntity(
    id = this.id.id,
    name = this.name.name.replaceFirstChar { it.uppercase() }
)

@JvmName("listSimplePokedexAsEntity")
private fun List<SimplePokedex>.asEntity(): List<PokedexEntity> = this.map(SimplePokedex::asEntity)

private fun SimplePokedex.asEntity(): PokedexEntity = PokedexEntity(
    id = this.id.id,
    regionId = this.regionId.id,
    name = this.name.name.replace(
        "-",
        " "
    ).replaceFirstChar { it.uppercase() }
)

private fun RegionWithPokedexes.asDomain(): Region =
    Region(
        id = ID(this.region.id),
        name = Name(this.region.name),
        pokedexes = this.pokedexes.asDomain()
    )

@JvmName("listPokedexEntityAsDomain")
private fun List<PokedexEntity>.asDomain(): List<SimplePokedex> = this.map(PokedexEntity::asDomain)

private fun PokedexEntity.asDomain(): SimplePokedex = SimplePokedex(
    id = ID(this.id),
    regionId = ID(this.regionId),
    name = Name(this.name)
)