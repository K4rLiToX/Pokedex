package com.carlosdiestro.features.home

import com.carlosdiestro.features.home.models.PokedexPlo
import com.carlosdiestro.features.home.models.PokemonPlo
import com.carlosdiestro.features.home.models.RegionPlo

internal sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object DataNotAvailable : HomeUiState
    data class Success(val data: HomeState) : HomeUiState
}

internal data class HomeState(
    val currentRegion: RegionPlo?,
    val currentRegionPokedexes: List<PokedexPlo>,
)

internal sealed interface RegionsUiState {
    data object Loading : RegionsUiState
    data object DataNotAvailable : RegionsUiState
    data class Success(val regions: List<RegionPlo>) : RegionsUiState
}

internal sealed interface PokedexEntriesUiState {
    data object Loading : PokedexEntriesUiState
    data object DataNotAvailable : PokedexEntriesUiState
    data class Success(val pokemons: List<PokemonPlo>) : PokedexEntriesUiState
}

