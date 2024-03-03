package com.carlosdiestro.network

import com.carlosdiestro.network.region.dtos.RegionsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

internal class PokeApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPokemonRegions(): Result<RegionsDto> = runCatching {
        client
            .get(ApiRoutes.Regions)
            .body<RegionsDto>()
    }
}