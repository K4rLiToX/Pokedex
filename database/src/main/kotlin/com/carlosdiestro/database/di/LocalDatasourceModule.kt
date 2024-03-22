package com.carlosdiestro.database.di

import com.carlosdiestro.core.common.requests.RequestLocalDatasource
import com.carlosdiestro.core.pokedex.data.PokedexLocalDatasource
import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.database.pokedex.PokedexLocalDatasourceImpl
import com.carlosdiestro.database.region.RegionLocalDatasourceImpl
import com.carlosdiestro.database.requestMetadata.RequestLocalDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocalDatasourceModule {

    @Singleton
    @Binds
    fun bindRegionLocalDatasource(impl: RegionLocalDatasourceImpl): RegionLocalDatasource

    @Singleton
    @Binds
    fun bindRequestMetadataLocalDatasource(impl: RequestLocalDatasourceImpl): RequestLocalDatasource

    @Singleton
    @Binds
    fun bindPokedexLocalDatasource(impl: PokedexLocalDatasourceImpl): PokedexLocalDatasource
}