package com.carlosdiestro.features.home

import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class HomeService @Inject constructor(
    private val regionRepository: RegionRepository
) {

    val regions: Flow<Result<List<Region>>> = flow { regionRepository.getPokemonRegions() }
}