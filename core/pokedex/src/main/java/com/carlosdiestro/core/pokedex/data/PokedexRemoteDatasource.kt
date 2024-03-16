package com.carlosdiestro.core.pokedex.data

import com.carlosdiestro.core.common.models.SyncState
import com.carlosdiestro.core.pokedex.domain.SimplePokemon

interface PokedexRemoteDatasource {

    suspend fun getPokedexEntries(pokedexId: Int, eTag: String = ""): SyncState<List<SimplePokemon>>
}