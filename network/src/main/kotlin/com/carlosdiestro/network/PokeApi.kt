package com.carlosdiestro.network

import com.carlosdiestro.network.region.dtos.RegionDto
import com.carlosdiestro.network.region.dtos.RegionsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import javax.inject.Inject

internal class PokeApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPokemonRegions(): Result<RegionsDto> = runCatching {
        client
            .get(ApiRoutes.Regions)
            .body<RegionsDto>()
    }

    suspend fun getPokemonRegion(regionId: Int): Result<RegionDto> = runCatching {
        client.get(ApiRoutes.Regions) {
            this.url {
                it.appendPathSegments(regionId.toString())
            }
        }.body<RegionDto>()
    }
}