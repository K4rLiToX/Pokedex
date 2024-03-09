package com.carlosdiestro.features.home

import com.carlosdiestro.core.region.data.DataResource
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

    val regions: Flow<UiResult<List<SimpleRegion>>> = flow {
        regionRepository.getPokemonRegions()
            .onStart { emit(UiResult.Loading) }
            .collect { resource ->
                val result = when (resource) {
                    is DataResource.Failure -> {
                        resource.data?.let { regions ->
                            if (regions.isNotEmpty()) UiResult.Success(regions)
                            else UiResult.Failure(resource.exception)
                        } ?: UiResult.Failure(resource.exception)
                    }
                    is DataResource.Success -> UiResult.Success(resource.data)
                }
                emit(result)
            }
    }

    val defaultRegionId: Flow<Int> = flowOf(1)
    fun getRegion(regionId: Int): Flow<UiResult<Region?>> = flow {
        regionRepository.getPokemonRegion(ID(regionId))
            .onStart { emit(UiResult.Loading) }
            .collect { resource ->
                val result = when (resource) {
                    is DataResource.Failure -> {
                        resource.data?.let { region ->
                            UiResult.Success(region)
                        } ?: UiResult.Failure(resource.exception)
                    }
                    is DataResource.Success -> UiResult.Success(resource.data)
                }
                emit(result)
            }
    }
}