package com.carlosdiestro.features.pokemondetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.carlosdiestro.features.pokemondetails.PokemonDetailsPane
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(
    val id: Int,
)

fun NavController.navigateToPokemonDetails(
    pokemonId: Int,
) {
    navigate(PokemonDetails(id = pokemonId))
}

fun NavGraphBuilder.pokemonDetails(
    onBack: () -> Unit,
) {
    composable<PokemonDetails> {
        PokemonDetailsPane(
            onBack = onBack
        )
    }
}