package com.carlosdiestro.features.home

import com.carlosdiestro.features.home.models.RegionPlo

internal sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Error(val message: String?): HomeUiState

    data class Success(val data: HomeState): HomeUiState
}

internal data class HomeState(
    val region: String,
    val pokedexes: List<String>,
    val entries: List<String>
)

internal sealed interface RegionsUiState {
    data object Loading: RegionsUiState
    data class Error(val message: String?): RegionsUiState
    data class Success(val regions: List<RegionPlo>): RegionsUiState
}

