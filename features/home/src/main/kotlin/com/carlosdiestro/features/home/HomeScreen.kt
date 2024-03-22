package com.carlosdiestro.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosdiestro.features.home.HomeUiState.Success
import com.carlosdiestro.features.home.components.RegionsDialog
import com.carlosdiestro.features.home.components.rememberRegionsDialogState
import com.carlosdiestro.features.home.models.PokemonPlo
import com.carlosdiestro.features.home.models.RegionPlo
import kotlinx.coroutines.launch

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val regionsState by viewModel.regionsState.collectAsStateWithLifecycle()
    val pokedexEntriesState by viewModel.pokedexEntriesState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        regionsState = regionsState,
        pokedexEntriesState = pokedexEntriesState,
        onRegionClick = viewModel::updateCurrentRegion,
        onPokedexClick = viewModel::updatePokedex
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    state: HomeUiState,
    regionsState: RegionsUiState,
    pokedexEntriesState: PokedexEntriesUiState,
    onRegionClick: (RegionPlo) -> Unit,
    onPokedexClick: (Int) -> Unit,
) {
    val regionsDialogState = rememberRegionsDialogState()
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        when (state) {
            HomeUiState.DataNotAvailable -> 0
            HomeUiState.Loading          -> 0
            is Success                   -> state.data.currentRegionPokedexes.size
        }
    }

    val selectedTabIndex by remember(pagerState) {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    val coroutineScope = rememberCoroutineScope()

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

                        is Success                      -> {
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
            PokedexesTab(
                state = state,
                selectedTabIndex = selectedTabIndex,
                onTabClick = { index, id ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                    onPokedexClick(id)
                }
            )
            PokemonEntries(
                state = pokedexEntriesState,
                pagerState = pagerState
            )
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

@Composable
private fun PokedexesTab(
    state: HomeUiState,
    selectedTabIndex: Int,
    onTabClick: (Int, Int) -> Unit,
) {
    when (state) {
        HomeUiState.Loading -> Unit
        HomeUiState.DataNotAvailable -> Unit
        is Success -> {
            PokedexTabSuccess(
                data = state.data,
                selectedTabIndex = selectedTabIndex,
                onTabClick = onTabClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokedexTabSuccess(
    data: HomeState,
    selectedTabIndex: Int,
    onTabClick: (Int, Int) -> Unit,
) {
    val onlyOnePokedex = data.currentRegionPokedexes.size == 1
    if (onlyOnePokedex) return
    else {
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
                        onTabClick(index, pokedex.id)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PokemonEntries(
    state: PokedexEntriesUiState,
    pagerState: PagerState,
) {
    when (state) {
        PokedexEntriesUiState.DataNotAvailable -> DataNotAvailable()
        PokedexEntriesUiState.Loading          -> Loading()
        is PokedexEntriesUiState.Success       -> {
            PokemonEntriesSuccess(
                pokemons = state.pokemons,
                pagerState = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PokemonEntriesSuccess(
    pokemons: List<PokemonPlo>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        key = { it },
        userScrollEnabled = false,
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = pokemons,
                key = { pokemon -> pokemon.id }
            ) { pokemon ->
                Text(text = pokemon.name)
            }
        }
    }
}