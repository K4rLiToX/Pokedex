package com.carlosdiestro.pokedex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.carlosdiestro.pokedex.navigation.PokedexNavHost

@Composable
internal fun PokedexApp(
    appState: PokedexAppState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) { innerPadding ->
        PokedexNavHost(
            appState = appState,
            modifier = modifier
                .padding(innerPadding)
        )
    }
}