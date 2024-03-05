package com.carlosdiestro.network.pokedex

import androidx.core.net.toUri
import com.carlosdiestro.core.pokedex.data.PokedexRemoteDatasource
import com.carlosdiestro.core.pokedex.domain.ID
import com.carlosdiestro.core.pokedex.domain.Name
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.RegionID
import com.carlosdiestro.network.PokeApi
import com.carlosdiestro.network.pokedex.dtos.SimplePokedexDto
import javax.inject.Inject

internal class PokedexRemoteDatasourceImpl @Inject constructor(
    private val api: PokeApi
) : PokedexRemoteDatasource {

    override suspend fun getRegionPokedexes(regionId: RegionID): Result<List<Pokedex>> =
        api.getRegionPokedexes(regionId.regionId).asDomain(regionId)
}

private fun Result<List<SimplePokedexDto>>.asDomain(regionId: RegionID): Result<List<Pokedex>> =
    this.mapCatching { pokedexes -> pokedexes.asDomain(regionId) }

private fun List<SimplePokedexDto>.asDomain(regionId: RegionID): List<Pokedex> = this.map {
    it.asDomain(regionId)
}

private fun SimplePokedexDto.asDomain(regionId: RegionID): Pokedex = Pokedex(
    id = ID(this.url.extractId()),
    regionId = regionId,
    name = Name(this.name)
)

private fun String.extractId(): Int = this.toUri().lastPathSegment?.toInt() ?: 1