package com.carlosdiestro.database.di

import com.carlosdiestro.core.region.data.RegionLocalDatasource
import com.carlosdiestro.database.region.RegionLocalDatasourceImpl
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
}