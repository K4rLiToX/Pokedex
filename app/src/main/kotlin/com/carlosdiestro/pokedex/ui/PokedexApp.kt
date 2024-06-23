package com.carlosdiestro.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.carlosdiestro.features.pokemonEntriesListDetails.PokemonEntriesListDetailsRoute

@Composable
internal fun PokedexApp(
    appState: PokedexAppState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PokemonEntriesListDetailsRoute()
    }
}