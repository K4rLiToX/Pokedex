package com.carlosdiestro.features.home

import android.util.Log
import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.SyncResult
import com.carlosdiestro.core.pokedex.domain.PokedexRepository
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.features.home.models.UiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val HomeServiceTag = "Home Service"

internal class HomeService @Inject constructor(
    private val regionRepository: RegionRepository,
    private val pokedexRepository: PokedexRepository,
) {

    fun observeRegions(): Flow<UiResult<List<SimpleRegion>, Nothing>> = regionRepository
        .observePokemonRegions()
        .map { regions ->
            if (regions.isEmpty()) UiResult.Empty()
            else UiResult.Success(regions)
        }
        .catch {
            Log.e(
                HomeServiceTag,
                it.message,
                it
            )
            UiResult.Failure()
        }

    fun observeRegion(regionId: Int): Flow<UiResult<Region, Int>> = regionRepository
        .observePokemonRegion(ID(regionId))
        .map { region ->
            region?.let {
                if (region.pokedexes.isNotEmpty()) UiResult.Success(it)
                else UiResult.Empty(regionId)
            } ?: UiResult.Empty(regionId)
        }
        .catch {
            Log.e(
                HomeServiceTag,
                it.message,
                it
            )
            UiResult.Failure()
        }

    fun observePokedex(pokedexId: Int): Flow<UiResult<List<SimplePokemon>, Int>> =
        pokedexRepository.observePokedexEntries(ID(pokedexId))
            .map { pokemons ->
                if (pokemons.isEmpty()) UiResult.Empty(pokedexId)
                else UiResult.Success(pokemons)

            }
            .catch {
                Log.e(
                    HomeServiceTag,
                    it.message,
                    it
                )
                UiResult.Failure()
            }

    suspend fun synchronizePokemonRegions(): SyncResult =
        regionRepository.synchronizePokemonRegions()

    suspend fun synchronizePokemonRegion(regionId: Int): SyncResult =
        regionRepository.synchronizePokemonRegion(ID(regionId))

    suspend fun synchronizePokedex(pokedexId: Int): SyncResult =
        pokedexRepository.synchronizePokemonEntries(ID(pokedexId))

    val defaultRegionId: Flow<Int> = flowOf(1)
}