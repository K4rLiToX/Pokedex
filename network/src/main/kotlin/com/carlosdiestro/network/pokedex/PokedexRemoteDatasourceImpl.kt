package com.carlosdiestro.network.pokedex

import androidx.core.net.toUri
import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name
import com.carlosdiestro.core.common.models.SyncState
import com.carlosdiestro.core.pokedex.data.PokedexRemoteDatasource
import com.carlosdiestro.core.pokedex.domain.PokedexIndex
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import com.carlosdiestro.network.ApiResult
import com.carlosdiestro.network.PokeApi
import com.carlosdiestro.network.pokedex.dto.PokemonEntryDto
import javax.inject.Inject

internal class PokedexRemoteDatasourceImpl @Inject constructor(
    private val api: PokeApi,
) : PokedexRemoteDatasource {

    override suspend fun getPokedexEntries(pokedexId: Int, eTag: String): SyncState<List<SimplePokemon>> {
        return when (val result = api.getPokedexEntries(pokedexId, eTag)) {
            is ApiResult.Success        -> {
                SyncState.Success(
                    data = result.data.entries.asDomain(),
                    expireDate = result.expireDate,
                    eTag = result.eTag
                )
            }

            ApiResult.RedirectException -> SyncState.NotModified
            ApiResult.NoDataAvailable   -> SyncState.NotAvailable
        }
    }
}

private fun List<PokemonEntryDto>.asDomain(): List<SimplePokemon> =
    this.map { it.asDomain() }

private fun PokemonEntryDto.asDomain(): SimplePokemon = SimplePokemon(
    id = ID(this.pokemon.url.extractId()),
    index = PokedexIndex(this.index),
    name = Name(this.pokemon.name)
)

private fun String.extractId(): Int = this.toUri().lastPathSegment?.toInt() ?: 1