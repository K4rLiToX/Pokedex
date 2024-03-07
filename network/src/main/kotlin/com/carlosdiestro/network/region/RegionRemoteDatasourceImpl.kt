package com.carlosdiestro.network.region

import androidx.core.net.toUri
import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimplePokedex
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.network.PokeApi
import com.carlosdiestro.network.region.dtos.RegionDto
import com.carlosdiestro.network.region.dtos.RegionsDto
import com.carlosdiestro.network.region.dtos.SimplePokedexDto
import com.carlosdiestro.network.region.dtos.SimpleRegionDto
import javax.inject.Inject

internal class RegionRemoteDatasourceImpl @Inject constructor(
    private val api: PokeApi
) : RegionRemoteDatasource {

    override suspend fun getPokemonRegions(): Result<List<SimpleRegion>> {
        return api.getPokemonRegions().asDomain()
    }

    override suspend fun getPokemonRegion(regionId: Int): Result<Region> {
        return api.getPokemonRegion(regionId).asDomain()
    }
}

private fun Result<RegionsDto>.asDomain(): Result<List<SimpleRegion>> =
    this.mapCatching { regions -> regions.asDomain() }

private fun RegionsDto.asDomain(): List<SimpleRegion> = this.region.asDomain()

private fun List<SimpleRegionDto>.asDomain(): List<SimpleRegion> = this.map { region ->
    SimpleRegion(
        id = ID(region.url.extractId()),
        name = Name(region.name)
    )
}

@JvmName("regionDtoAsDomain")
private fun Result<RegionDto>.asDomain(): Result<Region> = this.mapCatching { region ->
    region.asDomain()
}

private fun RegionDto.asDomain(): Region = Region(
    id = ID(this.id),
    name = Name(this.name),
    pokedexes = this.pokedexes.asDomain(this.id)
)

private fun List<SimplePokedexDto>.asDomain(regionId: Int): List<SimplePokedex> =
    this.map { it.asDomain(regionId) }

private fun SimplePokedexDto.asDomain(regionId: Int): SimplePokedex = SimplePokedex(
    id = ID(this.url.extractId()),
    regionId = ID(regionId),
    name = Name(this.name)
)

private fun String.extractId(): Int = this.toUri().lastPathSegment?.toInt() ?: 1

