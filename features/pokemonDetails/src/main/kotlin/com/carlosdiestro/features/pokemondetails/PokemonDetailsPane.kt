package com.carlosdiestro.features.pokemondetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.BackdropValue.Revealed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.carlosdiestro.design_system.theme.PokedexIcons
import com.carlosdiestro.features.pokemondetails.components.AbilitySpec
import com.carlosdiestro.features.pokemondetails.components.EggGroupSpec
import com.carlosdiestro.features.pokemondetails.components.HeightSpec
import com.carlosdiestro.features.pokemondetails.components.Stat
import com.carlosdiestro.features.pokemondetails.components.Type
import com.carlosdiestro.features.pokemondetails.components.WeightSpec
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState.DataNotAvailable
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState.Empty
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState.Loading
import com.carlosdiestro.features.pokemondetails.state.PokemonDetailsUiState.Success
import com.carlosdiestro.pokemon.domain.models.PokemonAbility
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
        backgroundColor = viewModel.backgroundColor,
        modifier = Modifier
            .fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun PokemonDetailsPane(
    onBack: () -> Unit,
    state: PokemonDetailsUiState,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val scaffoldState = rememberBackdropScaffoldState(initialValue = Revealed)
    var topBarHeight by remember {
        mutableStateOf(0.dp)
    }
    val backLayerVisibility by animateFloatAsState(targetValue = scaffoldState.currentFraction, label = "back layer visibility")
    val titleVisibility by animateFloatAsState(targetValue = 1 - scaffoldState.currentFraction, label = "title visibility")

    BackdropScaffold(
        scaffoldState = scaffoldState,
        backLayerBackgroundColor = backgroundColor,
        frontLayerBackgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        frontLayerScrimColor = Color.Unspecified,
        frontLayerShape = MaterialTheme.shapes.extraLarge,
        peekHeight = topBarHeight,
        appBar = {
            TopAppBar(
                title = {
                    when (state) {
                        is Success -> {
                            val imageRequest = ImageRequest.Builder(LocalContext.current)
                                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${state.pokemon.entry.id.value}.gif")
                                .decoderFactory(ImageDecoderDecoder.Factory())
                                .crossfade(true)
                                .build()
                            Box {
                                Text(
                                    text = "#${state.pokemon.entry.order.value}",
                                    modifier = Modifier
                                        .alpha(backLayerVisibility)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = imageRequest,
                                        contentDescription = state.pokemon.entry.name.value,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .width(32.dp)
                                            .aspectRatio(1F)
                                            .alpha(titleVisibility)
                                    )
                                    Text(
                                        text = state.pokemon.entry.name.value,
                                        modifier = Modifier
                                            .alpha(titleVisibility)
                                    )
                                }
                            }
                        }

                        else       -> Unit
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = PokedexIcons.Back,
                            contentDescription = stringResource(id = R.string.data_not_available_state)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .onGloballyPositioned {
                        val heightPx = it.size.height
                        val heightDp = with(density) { heightPx.toDp() }
                        topBarHeight = heightDp
                    }
            )
        },
        backLayerContent = {
            Header(
                state = state,
                visibility = backLayerVisibility
            )
        },
        frontLayerContent = {
            when (state) {
                DataNotAvailable -> Unit
                Empty            -> Unit
                Loading          -> Unit
                is Success       -> {
                    val pokemon = state.pokemon
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        item {
                            Text(
                                text = stringResource(id = R.string.about_title),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .padding(top = 24.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Specs(
                                height = pokemon.height.value,
                                weight = pokemon.weight.value,
                                eggGroups = pokemon.eggGroups,
                                abilities = pokemon.abilities,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                        item {
                            Text(
                                text = stringResource(id = R.string.stats_title),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        items(
                            items = pokemon.stats
                        ) { stat ->
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
        modifier = modifier
    )
}

@Composable
private fun Header(
    state: PokemonDetailsUiState,
    visibility: Float,
    modifier: Modifier = Modifier,
) {
    when (state) {
        DataNotAvailable -> Unit
        Empty            -> Unit
        Loading          -> Unit
        is Success       -> {
            val name = state.pokemon.entry.name.value
            val types = state.pokemon.types
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${state.pokemon.entry.id.value}.gif")
                .decoderFactory(ImageDecoderDecoder.Factory())
                .crossfade(true)
                .build()
            Column(
                modifier = modifier
                    .alpha(visibility)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(140.dp)
                            .aspectRatio(1F)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    types.forEach { type ->
                        Type(label = type.value)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
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

@OptIn(ExperimentalMaterialApi::class)
private val BackdropScaffoldState.currentFraction: Float
    get() {
        val fraction = progress.fraction
        return when {
            currentValue == BackdropValue.Concealed && targetValue == BackdropValue.Concealed -> 0f
            currentValue == Revealed && targetValue == Revealed                               -> 1f
            currentValue == BackdropValue.Concealed && targetValue == Revealed                -> fraction
            else                                                                              -> 1f - fraction
        }
    }