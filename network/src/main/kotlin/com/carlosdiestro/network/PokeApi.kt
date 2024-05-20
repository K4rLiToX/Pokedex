package com.carlosdiestro.network

import com.carlosdiestro.network.ApiResult.DataNotAvailableException
import com.carlosdiestro.network.ApiResult.RedirectException
import com.carlosdiestro.network.ApiResult.Success
import com.carlosdiestro.network.pokedex.PokedexDto
import com.carlosdiestro.network.pokemon.PokemonDetailsDto
import com.carlosdiestro.network.pokemon.PokemonDto
import com.carlosdiestro.network.pokemon.PokemonSpeciesDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import javax.inject.Inject

internal class PokeApi @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun fetchNationalDex(): ApiResult<PokedexDto> = suspendRunCatching {
        client.get(ApiRoutes.NATIONAL_DEX_ENTRIES)
    }

    suspend fun fetchPokemon(id: Int): ApiResult<PokemonDto> = run {
        val species = fetchPokemonSpecies(id)
        val details = fetchPokemonDetails(id)

        handleResult(species, details)
    }

    private suspend fun fetchPokemonSpecies(id: Int): ApiResult<PokemonSpeciesDto> = suspendRunCatching {
        client.get(ApiRoutes.POKEMON_SPECIES) {
            url {
                appendPathSegments("$id")
            }
        }
    }

    private suspend fun fetchPokemonDetails(id: Int): ApiResult<PokemonDetailsDto> = suspendRunCatching {
        client.get(ApiRoutes.POKEMON_DETAILS) {
            url {
                appendPathSegments("$id")
            }
        }
    }

    private fun handleResult(
        species: ApiResult<PokemonSpeciesDto>,
        details: ApiResult<PokemonDetailsDto>,
    ): ApiResult<PokemonDto> = when {
        species is Success && details is Success                     -> {
            Success(
                PokemonDto(
                    types = details.data.types,
                    height = details.data.height,
                    weight = details.data.weight,
                    eggGroups = species.data.eggGroups,
                    abilities = details.data.abilities
                )
            )
        }

        species is RedirectException && details is RedirectException -> {
            RedirectException
        }

        else                                                         -> {
            DataNotAvailableException
        }
    }
}