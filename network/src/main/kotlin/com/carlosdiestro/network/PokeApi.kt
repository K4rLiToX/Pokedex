package com.carlosdiestro.network

import com.carlosdiestro.network.region.dtos.RegionDto
import com.carlosdiestro.network.region.dtos.RegionsDto
import com.carlosdiestro.network.pokedex.dtos.SimplePokedexDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class PokeApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPokemonRegions(): Result<RegionsDto> = runCatching {
        client
            .get(ApiRoutes.Regions)
            .body<RegionsDto>()
    }

    suspend fun getRegionPokedexes(regionId: Int): Result<List<SimplePokedexDto>> =
        getRegion(regionId).fold(
            onSuccess = { region -> Result.success(region.pokedexes) },
            onFailure = { e -> Result.failure(e) }
        )

    private suspend fun getRegion(regionId: Int): Result<RegionDto> = runCatching {
        client.get(ApiRoutes.Region) {
            parameter(ApiUrlParameters.RegionId, regionId)
        }.body<RegionDto>()
    }
}