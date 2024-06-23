package com.carlosdiestro.features.pokemonEntriesListDetails.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosdiestro.design_system.theme.PokedexTheme
import com.carlosdiestro.features.pokemonEntriesListDetails.R
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.PokemonEntriesUiState.DataNotAvailable
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.PokemonEntriesUiState.Empty
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.PokemonEntriesUiState.Loading
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.PokemonEntriesUiState.Success
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.components.PokemonEntry
import com.carlosdiestro.features.pokemonEntriesListDetails.entries.components.PokemonSearchBar
import com.carlosdiestro.pokemon.domain.models.PokemonEntry
import com.carlosdiestro.pokemon.domain.models.PokemonId
import com.carlosdiestro.pokemon.domain.models.PokemonName
import com.carlosdiestro.pokemon.domain.models.PokemonOrder
import com.carlosdiestro.pokemon.domain.models.SpriteUrl

@Composable
internal fun PokemonEntriesPane(
    onEntryClick: (Int, Int) -> Unit,
    viewModel: PokemonEntriesViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle(initialValue = Loading)

    PokemonEntriesPane(
        state = state,
        onEntryClick = onEntryClick
    )
}

@Composable
private fun PokemonEntriesPane(
    state: PokemonEntriesUiState,
    onEntryClick: (Int, Int) -> Unit = { _, _ -> },
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            PokemonSearchBar(
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {},
                onLeadingIconClick = {
                    query = ""
                },
                onTrailIconClick = { isActive ->
                    if (isActive) {
                        query = ""
                    } else {
                        Unit
                    }
                }
            ) {

            }
        }
    ) { innerPadding ->
        when (state) {
            is Loading -> LoadingLayout(Modifier.fillMaxSize())
            is Empty -> EmptyLayout(Modifier.fillMaxSize())
            is DataNotAvailable -> DataNotAvailableLayout(Modifier.fillMaxSize())
            is Success -> PokemonEntriesLayout(
                entries = state.entries,
                contentPadding = PokemonEntriesPaneTokens.getPokemonEntriesLayoutPadding(
                    top = innerPadding.calculateTopPadding()
                ),
                onEntryClick = onEntryClick,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun PokemonEntriesLayout(
    entries: List<PokemonEntry>,
    contentPadding: PaddingValues,
    onEntryClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(PokemonEntriesPaneTokens.CellMinWidth),
        verticalArrangement = Arrangement.spacedBy(PokemonEntriesPaneTokens.PokemonEntriesVerticalSpacing),
        horizontalArrangement = Arrangement.spacedBy(PokemonEntriesPaneTokens.PokemonEntriesHorizontalSpacing),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(
            items = entries,
            key = { it.id.value }
        ) { entry ->
            PokemonEntry(
                onClick = { onEntryClick(entry.id.value, it.toArgb()) },
                name = entry.name.value,
                order = "#${entry.order.value}",
                coverUrl = entry.spriteUrl.value
            )
        }
    }
}

@Composable
private fun LoadingLayout(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyLayout(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.empty_state),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DataNotAvailableLayout(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.data_not_available),
            textAlign = TextAlign.Center
        )
    }
}

@Immutable
private object PokemonEntriesPaneTokens {

    val CellMinWidth: Dp
        get() = 180.dp

    val PokemonEntriesVerticalSpacing: Dp
        get() = 16.dp

    val PokemonEntriesHorizontalSpacing: Dp
        get() = 16.dp

    @Composable
    fun getPokemonEntriesLayoutPadding(
        top: Dp,
    ) = PaddingValues(
        start = PokemonEntriesLayoutHorizontalPadding,
        top = top + PokemonEntriesLayoutExtraVerticalPadding,
        end = PokemonEntriesLayoutHorizontalPadding
    )

    private val PokemonEntriesLayoutHorizontalPadding: Dp
        get() = 16.dp

    private val PokemonEntriesLayoutExtraVerticalPadding: Dp
        get() = 32.dp
}

@Composable
@PreviewLightDark
@PreviewScreenSizes
private fun PokemonEntriesLayoutPreview() {
    PokedexTheme {
        Surface {
            PokemonEntriesPane(
                state = Success(
                    entries = pokemonEntries
                )
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PokemonEntriesEmptyLayoutPreview() {
    PokedexTheme {
        Surface {
            PokemonEntriesPane(
                state = Empty
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PokemonEntriesDataNotAvailableLayoutPreview() {
    PokedexTheme {
        Surface {
            PokemonEntriesPane(
                state = DataNotAvailable
            )
        }
    }
}

private val pokemonEntries = listOf(
    PokemonEntry(
        id = PokemonId(1),
        name = PokemonName("Bulbasaur"),
        order = PokemonOrder(1),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(2),
        name = PokemonName("Ivysaur"),
        order = PokemonOrder(2),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(3),
        name = PokemonName("Venusaur"),
        order = PokemonOrder(3),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(4),
        name = PokemonName("Charmander"),
        order = PokemonOrder(4),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(5),
        name = PokemonName("Charmeleon"),
        order = PokemonOrder(5),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(6),
        name = PokemonName("Charizard"),
        order = PokemonOrder(6),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(7),
        name = PokemonName("Squirtle"),
        order = PokemonOrder(7),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(8),
        name = PokemonName("Wartortle"),
        order = PokemonOrder(8),
        spriteUrl = SpriteUrl("")
    ),
    PokemonEntry(
        id = PokemonId(9),
        name = PokemonName("Blastoise"),
        order = PokemonOrder(9),
        spriteUrl = SpriteUrl("")
    ),
)