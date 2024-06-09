package com.carlosdiestro.features.pokemondetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.carlosdiestro.features.pokemondetails.PokemonDetailsPane
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(
    val id: Int,
)

fun NavGraphBuilder.pokemonDetails(
    onBack: () -> Unit,
) {
    composable<PokemonDetails> {
        PokemonDetailsPane(
            onBack = onBack
        )
    }
}