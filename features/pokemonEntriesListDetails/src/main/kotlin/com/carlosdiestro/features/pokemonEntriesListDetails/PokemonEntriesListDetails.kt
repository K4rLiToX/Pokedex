package com.carlosdiestro.features.pokemonEntriesListDetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.carlosdiestro.features.pokemonEntriesListDetails.details.PokemonDetailsPane
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.PokemonEntriesPane

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun PokemonEntriesListDetailsRoute() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Pair<Int, Int>>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    PokemonEntriesListDetailsScreen(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        currentDestination = navigator.currentDestination,
        navigateBack = navigator::navigateBack,
        navigateToDetails = { args ->
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, args)
        },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal
                )
            )
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun PokemonEntriesListDetailsScreen(
    directive: PaneScaffoldDirective,
    value: ThreePaneScaffoldValue,
    currentDestination: ThreePaneScaffoldDestinationItem<Pair<Int, Int>>?,
    navigateBack: () -> Unit,
    navigateToDetails: (Pair<Int, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    ListDetailPaneScaffold(
        directive = directive,
        value = value,
        listPane = {
            AnimatedPane {
                PokemonEntriesPane(
                    onEntryClick = { id, backgroundColor ->
                        navigateToDetails(id to backgroundColor)
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                currentDestination?.content?.let { (id, backgroundColor) ->
                    PokemonDetailsPane(
                        pokemonId = id,
                        backgroundColor = backgroundColor,
                        onBack = navigateBack
                    )
                }
            }
        },
        modifier = modifier
    )
}