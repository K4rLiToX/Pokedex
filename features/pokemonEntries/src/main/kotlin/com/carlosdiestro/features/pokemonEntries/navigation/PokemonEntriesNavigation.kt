package com.carlosdiestro.features.pokemonEntries.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.carlosdiestro.features.pokemonEntries.PokemonEntriesPane
import kotlinx.serialization.Serializable

@Serializable
object PokemonEntries

fun NavGraphBuilder.pokemonEntriesPane(
    onEntryClick: (Int) -> Unit,
) {
    composable<PokemonEntries> {
        PokemonEntriesPane(
            onEntryClick = onEntryClick
        )
    }
}