package com.carlosdiestro.network.di

import com.carlosdiestro.core.pokedex.data.PokedexRemoteDatasource
import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.network.pokedex.PokedexRemoteDatasourceImpl
import com.carlosdiestro.network.region.RegionRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteDatasourceModule {

    @Singleton
    @Binds
    fun bindRegionRemoteDatasource(impl: RegionRemoteDatasourceImpl): RegionRemoteDatasource

    @Singleton
    @Binds
    fun bindPokedexRemoteDatasource(impl: PokedexRemoteDatasourceImpl): PokedexRemoteDatasource
}