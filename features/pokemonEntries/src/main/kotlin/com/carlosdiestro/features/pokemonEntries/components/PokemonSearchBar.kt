package com.carlosdiestro.features.pokemonEntries.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosdiestro.design_system.theme.PokedexIcons
import com.carlosdiestro.features.pokemonEntries.R


/**
 * SearchBar stylized to match the Pokemon app needs and also automatically handles its own active state.
 * @param onTrailIconClick Called when the trailing icon is clicked. Due to having different active and inactive states, isActive is passed as a parameter.
 * @param content The content of the search bar. This is only displayed when the search bar is active.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onTrailIconClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    val horizontalPadding by animateDpAsState(
        targetValue = PokemonSearchBarTokens.getSearchHorizontalPadding(isActive),
        label = "Search bar horizontal padding"
    )

    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = isActive,
        onActiveChange = {
            isActive = it
        },
        leadingIcon = {
            if (isActive) {
                IconButton(
                    onClick = {
                        isActive = false
                        onLeadingIconClick()
                    }
                ) {
                    Icon(
                        imageVector = PokedexIcons.Back,
                        contentDescription = stringResource(id = R.string.back_action_description)
                    )
                }
            } else {
                Icon(
                    imageVector = PokedexIcons.Search,
                    contentDescription = stringResource(id = R.string.search_action_description)
                )
            }
        },
        placeholder = {
            Text(text = stringResource(id = R.string.searchbar_placeholder))
        },
        trailingIcon = {
            val imageVector = if (isActive) PokedexIcons.Clear else PokedexIcons.Settings
            val contentDescription = if (isActive) R.string.clear_query_action_description else R.string.settings_action_description

            IconButton(
                onClick = { onTrailIconClick(isActive) }
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = stringResource(id = contentDescription)
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        if (isActive) {
            content(this)
        }
    }
}

@Immutable
private object PokemonSearchBarTokens {

    @Composable
    fun getSearchHorizontalPadding(active: Boolean): Dp = when (active) {
        true -> ActiveSearchHorizontalPadding
        false -> InactiveSearchHorizontalPadding
    }

    private val InactiveSearchHorizontalPadding: Dp
        get() = 16.dp

    private val ActiveSearchHorizontalPadding: Dp
        get() = 0.dp
}