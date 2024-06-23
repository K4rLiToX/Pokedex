package com.carlosdiestro.features.pokemonEntriesListDetails.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val observePokemonDetails: ObservePokemonDetailsUseCase,
) : ViewModel() {

    fun getUiState(id: Int): StateFlow<PokemonDetailsUiState> =
        observePokemonDetails(PokemonId(id))
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