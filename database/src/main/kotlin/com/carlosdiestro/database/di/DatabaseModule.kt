package com.carlosdiestro.database.di

import android.content.Context
import androidx.room.Room
import com.carlosdiestro.database.PokedexDatabase
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexPokemonCrossRefDao
import com.carlosdiestro.database.pokemon.PokemonDao
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.requestMetadata.RequestMetadataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DatabaseName = "pokedex_db"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun providePokedexDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context,
        klass = PokedexDatabase::class.java,
        name = DatabaseName
    ).build()

    @Singleton
    @Provides
    fun provideRegionDao(db: PokedexDatabase): RegionDao = db.regionDao()

    @Singleton
    @Provides
    fun providePokedexDao(db: PokedexDatabase): PokedexDao = db.pokedexDao()

    @Singleton
    @Provides
    fun provideRequestMetadataDao(db: PokedexDatabase): RequestMetadataDao = db.requestMetadataDao()

    @Singleton
    @Provides
    fun providePokemonDao(db: PokedexDatabase): PokemonDao = db.pokemonDao()

    @Singleton
    @Provides
    fun providePokedexPokemonCrossRefDao(db: PokedexDatabase): PokedexPokemonCrossRefDao = db.pokedexPokemonCrossRefDao()
}