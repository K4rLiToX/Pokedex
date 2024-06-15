package com.carlosdiestro.features.pokemondetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.carlosdiestro.features.pokemondetails.navigation.PokemonDetails
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState
import com.carlosdiestro.pokemon.domain.Event
import com.carlosdiestro.pokemon.domain.models.PokemonId
import com.carlosdiestro.pokemon.domain.usecases.ObservePokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PokemonDetailsViewModel @Inject constructor(
    observePokemonDetails: ObservePokemonDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val pokemonId = savedStateHandle.toRoute<PokemonDetails>().id
    val backgroundColor = Color(savedStateHandle.toRoute<PokemonDetails>().backgroundColor)

    val uiState: StateFlow<PokemonDetailsUiState> = observePokemonDetails(PokemonId(pokemonId))
        .map { event ->
            when (event) {
                is Event.DataNotAvailable -> PokemonDetailsUiState.DataNotAvailable
                is Event.DataNotModified  -> PokemonDetailsUiState.Empty
                is Event.Success          -> {
                    PokemonDetailsUiState.Success(event.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = PokemonDetailsUiState.Loading,
            started = SharingStarted.WhileSubscribed(5000)
        )
}