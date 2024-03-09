package com.carlosdiestro.database.region

import com.carlosdiestro.core.region.data.RegionCache
import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
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
    private val pokedexDao: PokedexDao
) : RegionLocalDatasource {

    override suspend fun upsert(regions: List<SimpleRegion>) = regionDao.upsert(regions.asEntity())

    override suspend fun upsert(region: Region) {
        regionDao.upsert(region.asEntity())
        pokedexDao.upsert(region.pokedexes.asEntity())
    }

    override fun getAll(): Flow<List<SimpleRegion>> =
        regionDao.getAll().asDomain()

    override fun getRegion(regionId: Int): Flow<RegionCache?> =
        regionDao.getRegion(regionId).asCacheEntity()
}

private fun Flow<List<RegionEntity>>.asDomain(): Flow<List<SimpleRegion>> =
    this.map(List<RegionEntity>::asDomain)

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
    name = this.name.name.replaceFirstChar { it.uppercase() },
    lastFetched = 0L
)

private fun Region.asEntity(): RegionEntity = RegionEntity(
    id = this.id.id,
    name = this.name.name,
    lastFetched = System.currentTimeMillis()
)

@JvmName("listSimplePokedexAsEntity")
private fun List<SimplePokedex>.asEntity(): List<PokedexEntity> = this.map(SimplePokedex::asEntity)

private fun SimplePokedex.asEntity(): PokedexEntity = PokedexEntity(
    id = this.id.id,
    regionId = this.regionId.id,
    name = this.name.name.replace("-", " ").replaceFirstChar { it.uppercase() }
)

@JvmName("flowRegionWithPokedexesAsCacheEntity")
private fun Flow<RegionWithPokedexes?>.asCacheEntity(): Flow<RegionCache?> =
    this.map { it?.asCacheEntity() }

private fun RegionWithPokedexes.asCacheEntity(): RegionCache =
    RegionCache(
        id = this.region.id,
        name = this.region.name,
        pokedexes = this.pokedexes.asDomain(),
        lastAccessed = this.region.lastFetched
    )

@JvmName("listPokedexEntityAsDomain")
private fun List<PokedexEntity>.asDomain(): List<SimplePokedex> = this.map(PokedexEntity::asDomain)

private fun PokedexEntity.asDomain(): SimplePokedex = SimplePokedex(
    id = ID(this.id),
    regionId = ID(this.regionId),
    name = Name(this.name)
)