package com.carlosdiestro.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosdiestro.features.home.components.RegionsDialog
import com.carlosdiestro.features.home.components.rememberRegionsDialogState
import com.carlosdiestro.features.home.models.RegionPlo

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val regionsState by viewModel.regionsState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        regionsState = regionsState,
        onRegionClick = viewModel::updateCurrentRegion
    )
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    regionsState: RegionsUiState,
    onRegionClick: (RegionPlo) -> Unit
) {
    when (state) {
        is HomeUiState.Error -> Error()
        HomeUiState.Loading -> Loading()
        is HomeUiState.Success -> Success(
            data = state.data,
            regionsState = regionsState,
            onRegionClick = onRegionClick
        )
    }
}

@Composable
private fun Loading() {

}

@Composable
private fun Error() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Success(
    data: HomeState,
    regionsState: RegionsUiState,
    onRegionClick: (RegionPlo) -> Unit
) {
    val regionsDialogState = rememberRegionsDialogState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = data.currentRegion.name
                    )
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = regionsDialogState::openRegionsDialog
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(
                    start = 16.dp,
                    top = 24.dp,
                    end = 16.dp
                )
        ) {
            val onlyOnePokedex = data.currentRegionPokedexes.size == 1
            if (onlyOnePokedex) {

            } else {
                var selectedTabIndex by remember {
                    mutableIntStateOf(0)
                }

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex
                ) {
                    data.currentRegionPokedexes.forEachIndexed { index, pokedex ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            }
                        ) {
                            Text(
                                text = pokedex.name
                            )
                        }
                    }
                }
            }
        }
    }

    if (regionsDialogState.canOpenRegionsDialog) {
        RegionsDialog(
            state = regionsState,
            currentRegion = data.currentRegion,
            onRegionClick = onRegionClick,
            onDismiss = regionsDialogState::closeRegionsDialog
        )
    }
}