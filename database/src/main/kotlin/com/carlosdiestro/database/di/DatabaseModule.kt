package com.carlosdiestro.database.di

import android.content.Context
import androidx.room.Room
import com.carlosdiestro.database.PokedexDatabase
import com.carlosdiestro.database.region.RegionDao
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
}