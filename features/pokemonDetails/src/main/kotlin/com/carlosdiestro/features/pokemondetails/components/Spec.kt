package com.carlosdiestro.features.pokemondetails.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosdiestro.design_system.theme.PokedexIcons
import com.carlosdiestro.design_system.theme.PokedexTheme

@Composable
internal fun HeightSpec(
    value: String,
    modifier: Modifier = Modifier,
) {
    val formattedValue = "${value}m"
    BasicSpec(
        values = listOf(formattedValue),
        type = SpecType.Height,
        modifier = modifier
    )
}

@Composable
internal fun WeightSpec(
    value: String,
    modifier: Modifier = Modifier,
) {
    val formattedValue = "${value}Kg"
    BasicSpec(
        values = listOf(formattedValue),
        type = SpecType.Weight,
        modifier = modifier
    )
}

@Composable
internal fun EggGroupSpec(
    values: List<String>,
    modifier: Modifier = Modifier,
) {
    BasicSpec(
        values = values,
        type = SpecType.EggGroup,
        modifier = modifier
    )
}

@Composable
internal fun AbilitySpec(
    values: List<String>,
    modifier: Modifier = Modifier,
) {
    BasicSpec(
        values = values,
        type = SpecType.Ability,
        modifier = modifier
    )
}

@Composable
private fun BasicSpec(
    values: List<String>,
    type: SpecType,
    modifier: Modifier = Modifier,
) {

    OutlinedCard(
        onClick = {},
        border = SpecTokens.borderStroke,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = type.icon),
            contentDescription = type.name,
            modifier = Modifier
                .padding(SpecTokens.ContainerPadding)
        )
        Spacer(modifier = Modifier.height(SpecTokens.IconValuesSpacing))
        Column(
            verticalArrangement = Arrangement.spacedBy(SpecTokens.ValuesSpacing),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpecTokens.ContainerPadding)
        ) {
            values.forEach { value ->
                Text(
                    text = value,
                    style = SpecTokens.ValueTextStyle
                )
            }
        }
    }
}

@Immutable
private object SpecTokens {

    val ValueTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.bodyMedium

    val IconValuesSpacing: Dp
        get() = 16.dp

    val ValuesSpacing: Dp
        get() = 8.dp

    val ContainerPadding: PaddingValues
        get() = PaddingValues(
            start = ContainerHorizontalPadding,
            top = ContainerVerticalPadding,
            end = ContainerHorizontalPadding,
            bottom = ContainerVerticalPadding
        )

    private val ContainerHorizontalPadding: Dp
        get() = 12.dp

    private val ContainerVerticalPadding: Dp
        get() = 12.dp

    val borderStroke: BorderStroke
        @Composable
        get() = BorderStroke(
            width = CardDefaults.outlinedCardBorder().width,
            color = MaterialTheme.colorScheme.outline
        )
}

private enum class SpecType(@DrawableRes val icon: Int) {
    Height(PokedexIcons.Height),
    Weight(PokedexIcons.Weight),
    EggGroup(PokedexIcons.Group),
    Ability(PokedexIcons.Abilities)
}

@Composable
@PreviewLightDark
private fun SpecPreview() {
    PokedexTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeightSpec(
                    value = "0,7"
                )
                WeightSpec(
                    value = "6,9"
                )
                EggGroupSpec(
                    values = listOf("Monster", "Plant")
                )
                AbilitySpec(
                    values = listOf("Overgrow", "Chlorophyll")
                )
            }
        }
    }
}