package com.carlosdiestro.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberPokedexAppState(
    navController: NavHostController = rememberNavController(),
): PokedexAppState =
    remember(navController) {
        PokedexAppState(navController)
    }

@Stable
internal class PokedexAppState(
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

}