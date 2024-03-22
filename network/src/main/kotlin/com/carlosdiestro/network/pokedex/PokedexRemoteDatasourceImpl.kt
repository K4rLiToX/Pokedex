package com.carlosdiestro.network.pokedex

import androidx.core.net.toUri
import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name
import com.carlosdiestro.core.common.models.SyncState
import com.carlosdiestro.core.pokedex.data.PokedexRemoteDatasource
import com.carlosdiestro.core.pokedex.domain.PokedexOrder
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import com.carlosdiestro.core.pokedex.domain.Url
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
    order = PokedexOrder(this.index),
    name = Name(this.pokemon.name),
    spriteUrl = Url("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${this.pokemon.url.extractId()}.png")
)

private fun String.extractId(): Int = this.toUri().lastPathSegment?.toInt() ?: 1