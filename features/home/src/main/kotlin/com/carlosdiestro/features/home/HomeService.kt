package com.carlosdiestro.features.home

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import com.carlosdiestro.features.home.models.UiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class HomeService @Inject constructor(
    private val regionRepository: RegionRepository
) {

    val regions: Flow<Result<List<SimpleRegion>>> = regionRepository.getPokemonRegions()

    val defaultRegionId: Flow<Int> = flowOf(1)
    fun getRegion(regionId: Int): Flow<UiResult<Region>> = flow {
        regionRepository.getPokemonRegion(ID(regionId))
            .onStart { emit(UiResult.Loading) }
            .collect { result ->
                val value = result.fold(
                    onSuccess = { region ->
                        UiResult.Success(region)
                    },
                    onFailure = { e ->
                        UiResult.Failure(e)
                    }
                )
                emit(value)
            }
    }
}