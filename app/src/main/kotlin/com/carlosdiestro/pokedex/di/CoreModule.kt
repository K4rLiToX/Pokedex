package com.carlosdiestro.pokedex.di

import com.carlosdiestro.core.common.requests.RequestRepository
import com.carlosdiestro.core.common.requests.RequestRepositoryImpl
import com.carlosdiestro.core.region.data.RegionRepositoryImpl
import com.carlosdiestro.core.region.domain.RegionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Singleton
    @Binds
    fun bindRegionRepository(impl: RegionRepositoryImpl): RegionRepository

    @Singleton
    @Binds
    fun bindRequestRepository(impl: RequestRepositoryImpl): RequestRepository
}