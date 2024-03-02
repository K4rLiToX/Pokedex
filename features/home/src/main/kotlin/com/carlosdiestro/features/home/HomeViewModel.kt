package com.carlosdiestro.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.features.home.models.RegionPlo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    service: HomeService
) : ViewModel() {
    private var _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state = _state.asStateFlow()

    private var _currentRegion: MutableStateFlow<RegionPlo?> = MutableStateFlow(null)
    val currentRegion = _currentRegion.asStateFlow()

    val regions: StateFlow<RegionsUiState> = service.regions
        .map { result ->
            result.fold(
                onSuccess = { regions ->
                    val mappedRegions = regions.asPlo()
                    if (currentRegion.value == null) {
                        _currentRegion.update { mappedRegions[0] }
                    }
                    RegionsUiState.Success(mappedRegions)
                },
                onFailure = { e ->
                    RegionsUiState.Error(e.message)
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RegionsUiState.Loading
        )

    fun updateCurrentRegion(region: RegionPlo) {
        _currentRegion.update { region }
    }
}

private fun List<Region>.asPlo(): List<RegionPlo> = this.map { region -> region.asPlo() }

private fun Region.asPlo(): RegionPlo = RegionPlo(
    id = this.id.id,
    name = this.name.name.replaceFirstChar { it.uppercase() }
)

