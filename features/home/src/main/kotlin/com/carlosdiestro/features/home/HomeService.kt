package com.carlosdiestro.features.home

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class HomeService @Inject constructor(
    private val regionRepository: RegionRepository
) {

    val regions: Flow<Result<List<SimpleRegion>>> = regionRepository.getPokemonRegions()

    val defaultRegionId: Flow<Int> = flow { emit(1) }
    fun getRegion(regionId: Int): Flow<Result<Region>> =
        regionRepository.getPokemonRegion(ID(regionId))
}