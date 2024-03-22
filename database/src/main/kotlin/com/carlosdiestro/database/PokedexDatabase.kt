package com.carlosdiestro.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosdiestro.database.pokedex.PokedexDao
import com.carlosdiestro.database.pokedex.PokedexEntity
import com.carlosdiestro.database.pokedex.PokedexPokemonCrossRef
import com.carlosdiestro.database.pokedex.PokedexPokemonCrossRefDao
import com.carlosdiestro.database.pokemon.PokemonDao
import com.carlosdiestro.database.pokemon.PokemonEntity
import com.carlosdiestro.database.region.RegionDao
import com.carlosdiestro.database.region.RegionEntity
import com.carlosdiestro.database.requestMetadata.RequestMetadataDao
import com.carlosdiestro.database.requestMetadata.RequestMetadataEntity

@Database(
    entities = [
        RegionEntity::class,
        PokedexEntity::class,
        RequestMetadataEntity::class,
        PokemonEntity::class,
        PokedexPokemonCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
internal abstract class PokedexDatabase : RoomDatabase() {

    abstract fun regionDao(): RegionDao
    abstract fun pokedexDao(): PokedexDao
    abstract fun requestMetadataDao(): RequestMetadataDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokedexPokemonCrossRefDao(): PokedexPokemonCrossRefDao
}