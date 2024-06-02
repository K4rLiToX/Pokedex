package com.carlosdiestro.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.carlosdiestro.features.pokemonEntries.navigation.PokemonEntries
import com.carlosdiestro.features.pokemonEntries.navigation.pokemonEntriesPane
import com.carlosdiestro.pokedex.ui.PokedexAppState

@Composable
internal fun PokedexNavHost(
    appState: PokedexAppState,
    modifier: Modifier = Modifier,
    startDestination: Any = PokemonEntries,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        pokemonEntriesPane()
    }
}