package com.carlosdiestro.network.region

import android.util.Log
import androidx.core.net.toUri
import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.network.PokeApi
import com.carlosdiestro.network.region.dtos.RegionDto
import com.carlosdiestro.network.region.dtos.RegionsDto
import javax.inject.Inject

internal class RegionRemoteDatasourceImpl @Inject constructor(
    private val api: PokeApi
) : RegionRemoteDatasource {

    override suspend fun getPokemonRegions(): Result<List<Region>> {
        return api.getPokemonRegions().asDomain().also {
            it.onSuccess { regions ->
                Log.d("PokeApi", "Regions: $regions")
            }.onFailure { e ->
                Log.e("PokeApi", "Error", e)
            }
        }
    }
}

private fun Result<RegionsDto>.asDomain(): Result<List<Region>> =
    this.mapCatching { regions -> regions.asDomain() }

private fun RegionsDto.asDomain(): List<Region> = this.region.asDomain()

private fun List<RegionDto>.asDomain(): List<Region> = this.map { region ->
    Region(
        id = ID(region.url.extractId()),
        name = Name(region.name)
    )
}

private fun String.extractId(): Int = this.toUri().lastPathSegment?.toInt() ?: 1

