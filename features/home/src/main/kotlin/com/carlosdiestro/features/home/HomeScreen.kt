package com.carlosdiestro.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val currentRegion by viewModel.currentRegion.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        regionsState = regionsState,
        currentRegion = currentRegion,
        onRegionClick = viewModel::updateCurrentRegion
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeUiState,
    regionsState: RegionsUiState,
    currentRegion: RegionPlo?,
    onRegionClick: (RegionPlo) -> Unit
) {
    val regionsDialogState = rememberRegionsDialogState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = currentRegion?.name ?: ""
                    )
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
            when (state) {
                is HomeUiState.Error -> {

                }

                HomeUiState.Loading -> {

                }

                is HomeUiState.Success -> {

                }
            }
        }
    }

    if (regionsDialogState.canOpenRegionsDialog) {
        RegionsDialog(
            state = regionsState,
            currentRegion = currentRegion,
            onRegionClick = onRegionClick,
            onDismiss = regionsDialogState::closeRegionsDialog
        )
    }
}

@Composable
private fun Loading() {

}

@Composable
private fun Error() {

}

@Composable
private fun Success() {

}