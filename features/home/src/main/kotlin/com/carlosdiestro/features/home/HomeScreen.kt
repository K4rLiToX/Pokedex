package com.carlosdiestro.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosdiestro.features.home.components.RegionsDialog
import com.carlosdiestro.features.home.components.rememberRegionsDialogState
import com.carlosdiestro.features.home.models.RegionPlo

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val regionsState by viewModel.regionsState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        regionsState = regionsState,
        onRegionClick = viewModel::updateCurrentRegion
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeUiState,
    regionsState: RegionsUiState,
    onRegionClick: (RegionPlo) -> Unit,
) {
    val regionsDialogState = rememberRegionsDialogState()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    when (state) {
                        is HomeUiState.DataNotAvailable -> Unit
                        HomeUiState.Loading             -> {
                            Text(
                                text = "..."
                            )
                        }

                        is HomeUiState.Success          -> {
                            state.data.currentRegion?.name?.let {
                                Text(
                                    text = it
                                )
                            }
                        }
                    }

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
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (state) {
                is HomeUiState.DataNotAvailable -> DataNotAvailable()
                HomeUiState.Loading             -> Loading()
                is HomeUiState.Success          -> Success(
                    data = state.data
                )
            }
        }
    }

    RegionsDialog(
        dialogState = regionsDialogState,
        regionsState = regionsState,
        onRegionClick = onRegionClick,
        onDismiss = regionsDialogState::closeRegionsDialog
    )
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DataNotAvailable() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Error state icon",
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Sorry, data is not currently available at the moment. Try again later",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Success(
    data: HomeState,
) {
    val onlyOnePokedex = data.currentRegionPokedexes.size == 1
    if (onlyOnePokedex) {

    } else {
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }

        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            divider = {},
            modifier = Modifier
                .height(48.dp)
        ) {
            data.currentRegionPokedexes.forEachIndexed { index, pokedex ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = pokedex.name,
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier
                        .height(48.dp)
                )
            }
        }
    }
}