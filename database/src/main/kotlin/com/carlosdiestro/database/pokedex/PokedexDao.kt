package com.carlosdiestro.database.pokedex

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.carlosdiestro.database.pokemon.PokemonWithOrder
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PokedexDao {

    @Upsert
    suspend fun upsert(entities: List<PokedexEntity>)

    @Transaction
    @Query(
        """
         SELECT pokemonId as id, `order`, pokemons_table.name as name, pokemons_table.spriteUrl as spriteUrl
         FROM pokedex_pokemon_cross_ref 
         INNER JOIN pokemons_table
         ON pokemonId = pokemons_table.id
         WHERE pokedexId = :pokedexId
         ORDER BY `order`
        """
    )
    fun getPokedex(pokedexId: Int): Flow<List<PokemonWithOrder>>
}