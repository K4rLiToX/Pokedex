package com.carlosdiestro.database.pokedex

import com.carlosdiestro.core.pokedex.data.PokedexLocalDatasource
import com.carlosdiestro.core.pokedex.domain.ID
import com.carlosdiestro.core.pokedex.domain.Name
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID
import javax.inject.Inject

internal class PokedexLocalDatasourceImpl @Inject constructor(
    private val dao: PokedexDao
) : PokedexLocalDatasource {

    override suspend fun upsert(pokedexes: List<Pokedex>) = dao.upsert(pokedexes.asEntity())

    override suspend fun getRegionPokedexes(regionId: RegionID): List<Pokedex> =
        dao.getPokedexesByRegion(regionId.regionId).asDomain()
}

private fun List<Pokedex>.asEntity(): List<PokedexEntity> = this.map { it.asEntity() }

private fun Pokedex.asEntity(): PokedexEntity = PokedexEntity(
    id = this.id.id,
    regionId = this.regionId.regionId,
    name = this.name.name
)

private fun List<PokedexEntity>.asDomain(): List<Pokedex> = this.map { it.asDomain() }

private fun PokedexEntity.asDomain(): Pokedex = Pokedex(
    id = ID(this.id),
    regionId = RegionID(this.regionId),
    name = Name(this.name)
)