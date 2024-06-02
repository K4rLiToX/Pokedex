package com.carlosdiestro.features.pokemonEntries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.features.pokemonEntries.state.PokemonEntriesUiState
import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.usecases.ObservePokemonEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PokemonEntriesViewModel @Inject constructor(
    observePokemonEntries: ObservePokemonEntriesUseCase,
) : ViewModel() {

    val uiState: Flow<PokemonEntriesUiState> = observePokemonEntries()
        .map { event ->
            when (event) {
                is Event.DataNotAvailable -> PokemonEntriesUiState.DataNotAvailable
                is Event.DataNotModified  -> PokemonEntriesUiState.Empty
                is Event.Success          -> {
                    if (event.data.isEmpty()) PokemonEntriesUiState.Empty
                    else PokemonEntriesUiState.Success(event.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokemonEntriesUiState.Loading
        )
}