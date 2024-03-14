package com.carlosdiestro.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

private const val HomeBaseRoute = "home"

object HomeDestination {

    const val ROUTE = HomeBaseRoute

    fun getDestination(): String = HomeBaseRoute
}

fun NavController.navigateHome(
    navOptions: NavOptions? = null,
) = navigate(
    HomeDestination.getDestination(),
    navOptions
)

fun NavGraphBuilder.homeScreen() {
    composable(
        route = HomeDestination.ROUTE
    ) {
        HomeRoute()
    }
}