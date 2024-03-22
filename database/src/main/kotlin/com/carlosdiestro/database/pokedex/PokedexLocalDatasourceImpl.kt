package com.carlosdiestro.database.pokedex

import com.carlosdiestro.core.common.models.ID
import com.carlosdiestro.core.common.models.Name
import com.carlosdiestro.core.pokedex.data.PokedexLocalDatasource
import com.carlosdiestro.core.pokedex.domain.Pokedex
import com.carlosdiestro.core.pokedex.domain.PokedexOrder
import com.carlosdiestro.core.pokedex.domain.SimplePokemon
import com.carlosdiestro.core.pokedex.domain.Url
import com.carlosdiestro.database.pokemon.PokemonDao
import com.carlosdiestro.database.pokemon.PokemonEntity
import com.carlosdiestro.database.pokemon.PokemonWithOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PokedexLocalDatasourceImpl @Inject constructor(
    private val pokedexDao: PokedexDao,
    private val pokemonDao: PokemonDao,
    private val pokedexPokemonCrossRefDao: PokedexPokemonCrossRefDao,
) : PokedexLocalDatasource {

    override fun observePokedexEntries(pokedexId: ID): Flow<List<SimplePokemon>> =
        pokedexDao.getPokedex(pokedexId.id).map { it.asDomain() }

    override suspend fun upsert(pokedex: Pokedex) {
        pokemonDao.upsert(pokedex.pokemons.asEntity())
        val crossRefs = buildPokedexPokemonCrossRefs(pokedex.id.id, pokedex.pokemons)
        pokedexPokemonCrossRefDao.upsert(crossRefs)
    }

    private fun buildPokedexPokemonCrossRefs(pokedexId: Int, pokemons: List<SimplePokemon>): List<PokedexPokemonCrossRef> {
        return pokemons.map { pokemon ->
            PokedexPokemonCrossRef(
                pokedexId = pokedexId,
                pokemonId = pokemon.id.id,
                order = pokemon.order.order
            )
        }
    }
}

private fun List<PokemonWithOrder>.asDomain(): List<SimplePokemon> = this.map(PokemonWithOrder::asDomain)

private fun PokemonWithOrder.asDomain(): SimplePokemon = SimplePokemon(
    id = ID(this.id),
    name = Name(this.name),
    spriteUrl = Url(this.spriteUrl),
    order = PokedexOrder(this.order)
)

private fun List<SimplePokemon>.asEntity(): List<PokemonEntity> = this.map(SimplePokemon::asEntity)

private fun SimplePokemon.asEntity(): PokemonEntity = PokemonEntity(
    id = this.id.id,
    name = this.name.name.replaceFirstChar { it.uppercase() },
    spriteUrl = this.spriteUrl.url
)