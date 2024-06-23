package com.carlosdiestro.features.pokemonEntriesListDetails.details

import com.carlosdiestro.pokemon.domain.models.PokemonDetails

internal sealed class PokemonDetailsUiState {
    data object Loading : PokemonDetailsUiState()
    data object Empty : PokemonDetailsUiState()
    data object DataNotAvailable : PokemonDetailsUiState()
    data class Success(val pokemon: PokemonDetails) : PokemonDetailsUiState()
}