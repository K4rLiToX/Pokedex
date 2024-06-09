package com.carlosdiestro.features.pokemondetails

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.carlosdiestro.pokemon.domain.models.PokemonStat
import com.carlosdiestro.pokemon.domain.models.PokemonType

@Composable
internal fun PokemonDetailsPane(
    onBack: () -> Unit,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PokemonDetailsPane(
        onBack = onBack,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailsPane(
    onBack: () -> Unit,
    state: PokemonDetailsUiState,
    modifier: Modifier = Modifier,
) {
    val defaultContentColor = MaterialTheme.colorScheme.surface

    var contentColor by remember {
        mutableStateOf(defaultContentColor)
    }

    Scaffold(
        topBar = {
            when (state) {
                is PokemonDetailsUiState.Success -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = "#${state.pokemon.entry.order.value}"
                            )
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
                        }
                    )
                }

                else                             -> Unit
            }
        },
        contentColor = contentColor,
        modifier = modifier
            .fillMaxSize()
    ) { contentPadding ->
        when (state) {
            is PokemonDetailsUiState.Loading -> LoadingLayout()
            is PokemonDetailsUiState.DataNotAvailable -> DataNotAvailableLayout()
            is PokemonDetailsUiState.Empty -> EmptyLayout()
            is PokemonDetailsUiState.Success -> PokemonDetailsLayout(
                pokemon = state.pokemon,
                contentPadding = contentPadding,
                contentColor = {
                    contentColor = it
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun PokemonDetailsLayout(
    pokemon: PokemonDetails,
    contentPadding: PaddingValues,
    contentColor: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = pokemon.entry.spriteUrl.value,
            contentDescription = pokemon.entry.name.value,
            contentScale = ContentScale.Fit,
            onSuccess = {
                calcDominantColor(it.result.drawable) { color ->
                    contentColor(color)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .aspectRatio(1F)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(
                    start = 16.dp,
                    top = 90.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            Header(
                name = pokemon.entry.name.value,
                types = pokemon.types,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Specs(
                height = pokemon.height.value,
                weight = pokemon.weight.value,
                eggGroups = pokemon.eggGroups,
                abilities = pokemon.abilities
            )
            Stats(
                stats = pokemon.stats,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Header(
    name: String,
    types: List<PokemonType>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            types.forEach { type ->
                Type(label = type.value)
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
        maxItemsInEachRow = 2,
        modifier = modifier
    ) {
        HeightSpec(value = height.toString())
        WeightSpec(value = weight.toString())
        EggGroupSpec(values = eggGroup)
        AbilitySpec(values = ability)
    }
}

@Composable
private fun Stats(
    stats: List<PokemonStat>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.stats_title)
        )
        stats.forEach { stat ->
            Stat(
                label = stat.name,
                value = stat.value
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