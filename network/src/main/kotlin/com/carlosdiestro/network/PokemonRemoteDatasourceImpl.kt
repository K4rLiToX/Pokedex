package com.carlosdiestro.network

import com.carlosdiestro.network.ApiResult.DataNotAvailableException
import com.carlosdiestro.network.ApiResult.RedirectException
import com.carlosdiestro.network.ApiResult.Success
import com.carlosdiestro.network.mappers.asDomain
import com.carlosdiestro.pokemon.data.PokemonRemoteDatasource
import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import javax.inject.Inject

internal class PokemonRemoteDatasourceImpl @Inject constructor(
    private val api: PokeApi,
) : PokemonRemoteDatasource {

    override suspend fun fetchPokemons(): Event<List<PokemonEntry>> {
        return when (val response = api.fetchNationalDex()) {
            DataNotAvailableException -> Event.DataNotAvailable
            RedirectException         -> Event.DataNotModified
            is Success                -> Event.Success(
                response.data.pokemonEntries.asDomain()
            )
        }
    }

    override suspend fun fetchPokemon(id: Int): Event<PokemonDetails> {
       return when (val response = api.fetchPokemon(id)) {
           DataNotAvailableException -> Event.DataNotAvailable
           RedirectException         -> Event.DataNotModified
           is Success                -> Event.Success(
               response.data.asDomain()
           )
       }
    }
}