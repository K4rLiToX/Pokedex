package com.carlosdiestro.pokedex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.carlosdiestro.features.home.HomeDestination
import com.carlosdiestro.features.home.homeScreen

@Composable
fun PokedexApp(
    appState: PokedexAppState = rememberPokedexAppState()
) {
    Scaffold { contentPadding ->
        PokedexNavHost(
            appState = appState,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
fun PokedexNavHost(
    appState: PokedexAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        homeScreen()
    }
}