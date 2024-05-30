package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.PokemonRepository
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remote: PokemonRemoteDatasource,
) : PokemonRepository {

    override fun observePokemonEntries(): Flow<Event<List<PokemonEntry>>> = flow {
        emit(remote.fetchPokemons())
    }

    override fun observePokemonDetails(id: PokemonId): Flow<Event<PokemonDetails>> = flow {
        emit(remote.fetchPokemon(id.value))
    }
}