package com.carlosdiestro.network.di

import com.carlosdiestro.network.PokemonRemoteDatasourceImpl
import com.carlosdiestro.pokemon.data.PokemonRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatasourceModule {

    @Binds
    @Singleton
    fun bindPokemonRemoteDatasource(impl: PokemonRemoteDatasourceImpl): PokemonRemoteDatasource
}