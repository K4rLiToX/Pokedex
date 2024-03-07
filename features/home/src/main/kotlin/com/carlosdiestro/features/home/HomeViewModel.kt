package com.carlosdiestro.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimplePokedex
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.features.home.models.PokedexPlo
import com.carlosdiestro.features.home.models.RegionPlo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val service: HomeService
) : ViewModel() {

    private var _currentRegion: MutableStateFlow<RegionPlo?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeUiState> = combine(
        _currentRegion,
        service.defaultRegionId
    ) { currentRegion, defaultRegionId ->
        currentRegion?.id ?: defaultRegionId
    }.flatMapLatest { regionId ->
        service.getRegion(regionId)
    }.map { regionResult ->
        regionResult.fold(
            onSuccess = { region ->
                HomeUiState.Success(
                    HomeState(
                        currentRegion = region.asPlo(),
                        currentRegionPokedexes = region.pokedexes.asPlo()
                    )
                )
            },
            onFailure = { e ->
                HomeUiState.Error(message = e.localizedMessage)
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    val regionsState: StateFlow<RegionsUiState> = service.regions
        .map { regionsResult ->
            regionsResult.fold(
                onSuccess = { regions ->
                    val mappedRegions = regions.asPlo()
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

private fun List<SimpleRegion>.asPlo(): List<RegionPlo> = this.map { region -> region.asPlo() }

private fun SimpleRegion.asPlo(): RegionPlo = RegionPlo(
    id = this.id.id,
    name = this.name.name
)

private fun Region.asPlo(): RegionPlo = RegionPlo(
    id = this.id.id,
    name = this.name.name
)

@JvmName("listSimplePokedexAsPlo")
private fun List<SimplePokedex>.asPlo(): List<PokedexPlo> = this.map { it.asPlo() }

private fun SimplePokedex.asPlo(): PokedexPlo = PokedexPlo(
    id = this.id.id,
    regionId = this.regionId.id,
    name = this.name.name
)

