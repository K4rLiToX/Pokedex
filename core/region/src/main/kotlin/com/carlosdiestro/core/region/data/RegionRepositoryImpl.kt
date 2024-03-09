package com.carlosdiestro.core.region.data

import com.carlosdiestro.core.region.domain.ID
import com.carlosdiestro.core.region.domain.Name
import com.carlosdiestro.core.region.domain.Region
import com.carlosdiestro.core.region.domain.RegionRepository
import com.carlosdiestro.core.region.domain.SimpleRegion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val FETCH_EXPIRY_TIME = 259200000L

class RegionRepositoryImpl @Inject constructor(
    private val remote: RegionRemoteDatasource,
    private val local: RegionLocalDatasource
) : RegionRepository {

    override fun getPokemonRegions(): Flow<DataResource<List<SimpleRegion>>> = synchronizer(
        query = local::getAll,
        fetch = remote::getPokemonRegions,
        cache = local::upsert,
        shouldFetch = { regions ->
            val lastFetched = 16097653456789
            val isExpired = System.currentTimeMillis() - lastFetched >= FETCH_EXPIRY_TIME
            regions.isEmpty() || isExpired
        }
    )

    override fun getPokemonRegion(regionId: ID): Flow<DataResource<Region?>> = mapSynchronizer(
        query = { local.getRegion(regionId.id) },
        fetch = { remote.getPokemonRegion(regionId.id) },
        cache = local::upsert,
        shouldFetch = { cache ->
            cache == null || cache.pokedexes.isEmpty() || cache.isExpired()
        },
        mapper = { it?.asDomain() }
    )
}

private fun RegionCache.asDomain(): Region = Region(
    id = ID(this.id),
    name = Name(this.name),
    pokedexes = this.pokedexes
)

private fun RegionCache?.isExpired(): Boolean =
    System.currentTimeMillis() - (this?.lastAccessed
        ?: System.currentTimeMillis()) >= FETCH_EXPIRY_TIME