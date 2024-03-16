package com.carlosdiestro.network

import com.carlosdiestro.network.pokedex.dto.PokedexDto
import com.carlosdiestro.network.region.dtos.RegionDto
import com.carlosdiestro.network.region.dtos.RegionsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.appendPathSegments
import javax.inject.Inject

internal const val PokeApiTag = "PokeApi"

internal class PokeApi @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun getPokemonRegions(eTag: String): ApiResult<RegionsDto> =
        suspendRunCatching {
            client.get(ApiRoutes.REGIONS) {
                if (eTag.isNotEmpty()) {
                    header(
                        key = ApiHeadersKeys.IF_NONE_MATCH,
                        value = eTag
                    )
                }
            }
        }

    suspend fun getPokemonRegion(regionId: Int, eTag: String): ApiResult<RegionDto> =
        suspendRunCatching {
            client.get(ApiRoutes.REGIONS) {
                if (eTag.isNotEmpty()) {
                    header(
                        key = ApiHeadersKeys.IF_NONE_MATCH,
                        value = eTag
                    )
                }
                url {
                    it.appendPathSegments(regionId.toString())
                }
            }
        }

    suspend fun getPokedexEntries(pokedexId: Int, eTag: String): ApiResult<PokedexDto> =
        suspendRunCatching {
            client.get(ApiRoutes.POKEDEXES) {
                if (eTag.isNotEmpty()) {
                    header(
                        key = ApiHeadersKeys.IF_NONE_MATCH,
                        value = eTag
                    )
                }
                url {
                    it.appendPathSegments(pokedexId.toString())
                }
            }
        }
}