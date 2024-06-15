package com.carlosdiestro.features.pokemondetails

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.carlosdiestro.design_system.theme.PokedexIcons
import com.carlosdiestro.features.pokemondetails.components.AbilitySpec
import com.carlosdiestro.features.pokemondetails.components.EggGroupSpec
import com.carlosdiestro.features.pokemondetails.components.HeightSpec
import com.carlosdiestro.features.pokemondetails.components.Stat
import com.carlosdiestro.features.pokemondetails.components.Type
import com.carlosdiestro.features.pokemondetails.components.WeightSpec
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState
import com.carlosdiestro.pokemon.domain.models.PokemonAbility
import com.carlosdiestro.pokemon.domain.models.PokemonDetails
import com.carlosdiestro.pokemon.domain.models.PokemonEggGroup

@Composable
internal fun PokemonDetailsPane(
    onBack: () -> Unit,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PokemonDetailsPane(
        onBack = onBack,
        state = state,
        modifier = Modifier
            .fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailsPane(
    onBack: () -> Unit,
    state: PokemonDetailsUiState,
    modifier: Modifier = Modifier,
) {
    val defaultBackgroundColor = MaterialTheme.colorScheme.surface
    var backgroundColor by remember {
        mutableStateOf(defaultBackgroundColor)
    }
    val sheetState = rememberStandardBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val sheetHeight = LocalConfiguration.current.screenHeightDp.dp - 40.dp

    val contentVisibility by animateFloatAsState(
        targetValue = if (sheetState.targetValue == SheetValue.Expanded) 0F else 1F,
        label = "Content Visibility"
    )

    val titleVisibility by animateFloatAsState(
        targetValue = if (sheetState.targetValue == SheetValue.PartiallyExpanded) 0F else 1F,
        label = "Title Visibility"
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    when (state) {
                        is PokemonDetailsUiState.Success -> {
                            Text(
                                text = state.pokemon.entry.name.value,
                                modifier = Modifier
                                    .alpha(titleVisibility)
                            )
                        }

                        else                             -> Unit
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = PokedexIcons.Back,
                            contentDescription = stringResource(id = R.string.back_action_content_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        sheetContent = {
            when (state) {
                is PokemonDetailsUiState.Loading          -> LoadingLayout(Modifier.fillMaxSize())
                is PokemonDetailsUiState.Empty            -> EmptyLayout(Modifier.fillMaxSize())
                is PokemonDetailsUiState.DataNotAvailable -> DataNotAvailableLayout(Modifier.fillMaxSize())
                is PokemonDetailsUiState.Success          -> {
                    val pokemon = state.pokemon
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(sheetHeight)
                    ) {
                        Text(
                            text = stringResource(id = R.string.about_title),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .padding(top = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Specs(
                            height = pokemon.height.value,
                            weight = pokemon.weight.value,
                            eggGroups = pokemon.eggGroups,
                            abilities = pokemon.abilities,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = stringResource(id = R.string.stats_title),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        pokemon.stats.forEach { stat ->
                            Stat(
                                label = stat.name,
                                value = stat.value,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                            )
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 500.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetTonalElevation = 0.dp,
        sheetDragHandle = {},
        sheetShape = RectangleShape,
        modifier = modifier
    ) { innerPadding ->
        when (state) {
            is PokemonDetailsUiState.Loading          -> LoadingLayout(Modifier.fillMaxSize())
            is PokemonDetailsUiState.Empty            -> EmptyLayout(Modifier.fillMaxSize())
            is PokemonDetailsUiState.DataNotAvailable -> DataNotAvailableLayout(Modifier.fillMaxSize())
            is PokemonDetailsUiState.Success          -> {
                Column(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .background(backgroundColor)
                        .padding(horizontal = 16.dp)
                        .alpha(contentVisibility)
                ) {
                    Text(
                        text = state.pokemon.entry.name.value,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.pokemon.types.forEach { type ->
                            Type(label = type.value)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${state.pokemon.entry.id.value}.gif",
                            contentDescription = state.pokemon.entry.name.value,
                            contentScale = ContentScale.Fit,
                            onSuccess = {
                                calcDominantColor(it.result.drawable) { color ->
                                    backgroundColor = color
                                }
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .aspectRatio(1F)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Specs(
    height: Int,
    weight: Int,
    eggGroups: List<PokemonEggGroup>,
    abilities: List<PokemonAbility>,
    modifier: Modifier = Modifier,
) {
    val eggGroup = remember {
        eggGroups.map { it.value }
    }
    val ability = remember {
        abilities.map { it.value }
    }

    FlowRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        HeightSpec(value = height.toString())
        WeightSpec(value = weight.toString())
        EggGroupSpec(values = eggGroup)
        AbilitySpec(values = ability)
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
            text = stringResource(id = R.string.data_not_available_state),
            textAlign = TextAlign.Center
        )
    }
}

private fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    Palette.from(bmp).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { value ->
            onFinish(Color(value))
        }
    }
}