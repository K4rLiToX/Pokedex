package com.carlosdiestro.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberPokedexAppState(
    navController: NavHostController = rememberNavController()
): PokedexAppState = remember(
    navController
) {
    PokedexAppState(
        navController = navController
    )
}

@Stable
class PokedexAppState(
    val navController: NavHostController
)