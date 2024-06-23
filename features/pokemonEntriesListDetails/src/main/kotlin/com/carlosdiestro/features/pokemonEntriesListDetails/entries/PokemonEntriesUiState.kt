package com.carlosdiestro.features.pokemonEntriesListDetails.entries

import com.carlosdiestro.pokemon.domain.models.PokemonEntry

internal sealed class PokemonEntriesUiState {
    data object Loading : PokemonEntriesUiState()
    data object Empty : PokemonEntriesUiState()
    data object DataNotAvailable : PokemonEntriesUiState()
    data class Success(val entries: List<PokemonEntry>) : PokemonEntriesUiState()
}