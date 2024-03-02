package com.carlosdiestro.network.di

import com.carlosdiestro.core.region.data.RegionRemoteDatasource
import com.carlosdiestro.network.region.RegionRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ResourcesModule {

    @Singleton
    @Binds
    fun bindRegionRemoteDatasource(impl: RegionRemoteDatasourceImpl): RegionRemoteDatasource
}