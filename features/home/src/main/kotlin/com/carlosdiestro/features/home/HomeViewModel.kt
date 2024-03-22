package com.carlosdiestro.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.core.common.models.SyncResult
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.SimplePokedex
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.features.home.models.PokedexPlo
import com.carlosdiestro.features.home.models.PokemonPlo
import com.carlosdiestro.features.home.models.RegionPlo
import com.carlosdiestro.features.home.models.UiResult
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
    private val service: HomeService,
) : ViewModel() {

    private var _currentRegion: MutableStateFlow<RegionPlo?> = MutableStateFlow(null)

    private var _currentPokedexId: MutableStateFlow<Int> = MutableStateFlow(2)

    val pokedexEntriesState: StateFlow<PokedexEntriesUiState> = _currentPokedexId
        .flatMapLatest(service::observePokedex)
        .map { result ->
            when (result) {
                UiResult.Loading    -> PokedexEntriesUiState.Loading
                is UiResult.Empty   -> {
                    val syncResult = service.synchronizePokedex(result.extra!!)
                    when (syncResult) {
                        SyncResult.Success, SyncResult.NotNecessary -> PokedexEntriesUiState.Loading
                        SyncResult.Error                            -> PokedexEntriesUiState.DataNotAvailable
                    }
                }

                is UiResult.Success -> PokedexEntriesUiState.Success(
                    pokemons = result.value.asPlo()
                )

                is UiResult.Failure -> PokedexEntriesUiState.DataNotAvailable
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokedexEntriesUiState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeUiState> = combine(
        _currentRegion,
        service.defaultRegionId,
        ::chooseId
    ).flatMapLatest(service::observeRegion)
        .map { result ->
            when (result) {
                UiResult.Loading    -> HomeUiState.Loading
                is UiResult.Empty   -> {
                    val syncResult = service.synchronizePokemonRegion(result.extra!!)
                    when (syncResult) {
                        SyncResult.Success, SyncResult.NotNecessary -> HomeUiState.Loading
                        SyncResult.Error                            -> HomeUiState.DataNotAvailable
                    }
                }

                is UiResult.Success -> HomeUiState.Success(
                    HomeState(
                        currentRegion = result.value.asPlo(),
                        currentRegionPokedexes = result.value.pokedexes.asPlo()
                    )
                )

                is UiResult.Failure -> HomeUiState.DataNotAvailable
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    val regionsState: StateFlow<RegionsUiState> = service.observeRegions()
        .map { result ->
            when (result) {
                UiResult.Loading    -> RegionsUiState.Loading
                is UiResult.Empty   -> {
                    val syncResult = service.synchronizePokemonRegions()
                    when (syncResult) {
                        SyncResult.Success, SyncResult.NotNecessary -> RegionsUiState.Loading
                        SyncResult.Error                            -> RegionsUiState.DataNotAvailable
                    }
                }

                is UiResult.Success -> RegionsUiState.Success(result.value.asPlo())
                is UiResult.Failure -> RegionsUiState.DataNotAvailable
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RegionsUiState.Loading
        )

    fun updateCurrentRegion(region: RegionPlo) {
        _currentRegion.update { oldRegion ->
            oldRegion?.let {
                if (it.id != region.id) region
                else it
            } ?: region
        }
    }

    fun updatePokedex(pokedexId: Int) {
        _currentPokedexId.update { oldPokedexId ->
            if (oldPokedexId != pokedexId) pokedexId
            else oldPokedexId
        }
    }

    private fun chooseId(currentRegion: RegionPlo?, defaultRegionId: Int): Int =
        currentRegion?.id ?: defaultRegionId
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

@JvmName("listSimplePokemonAsPlo")
private fun List<SimplePokemon>.asPlo(): List<PokemonPlo> = this.map { it.asPlo() }

private fun SimplePokemon.asPlo(): PokemonPlo = PokemonPlo(
    id = this.id.id,
    name = this.name.name,
    spriteUrl = this.spriteUrl.url,
    order = this.order.order
)

