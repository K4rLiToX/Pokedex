package com.carlosdiestro.pokemon.data

import com.carlosdiestro.pokemon.domain.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PokemonDataModule {

    @Binds
    @Singleton
    fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}