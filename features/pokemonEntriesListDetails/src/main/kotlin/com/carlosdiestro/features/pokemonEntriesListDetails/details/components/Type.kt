package com.carlosdiestro.features.pokemonEntriesListDetails.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosdiestro.design_system.theme.Bug
import com.carlosdiestro.design_system.theme.Dark
import com.carlosdiestro.design_system.theme.Dragon
import com.carlosdiestro.design_system.theme.Electric
import com.carlosdiestro.design_system.theme.Fairy
import com.carlosdiestro.design_system.theme.Fighting
import com.carlosdiestro.design_system.theme.Fire
import com.carlosdiestro.design_system.theme.Flying
import com.carlosdiestro.design_system.theme.Ghost
import com.carlosdiestro.design_system.theme.Grass
import com.carlosdiestro.design_system.theme.Ground
import com.carlosdiestro.design_system.theme.Ice
import com.carlosdiestro.design_system.theme.Normal
import com.carlosdiestro.design_system.theme.Poison
import com.carlosdiestro.design_system.theme.PokedexTheme
import com.carlosdiestro.design_system.theme.Psychic
import com.carlosdiestro.design_system.theme.Rock
import com.carlosdiestro.design_system.theme.Steel
import com.carlosdiestro.design_system.theme.Water

@Composable
internal fun Type(
    label: String,
    modifier: Modifier = Modifier,
) {
    val containerColor = TypeTokens.getContainerColor(type = label)

    Surface(
        shape = TypeTokens.ContainerShape,
        contentColor = TypeTokens.ContentColor,
        border = TypeTokens.BorderStroke,
        modifier = modifier
            .requiredSizeIn(minWidth = TypeTokens.ContainerMinWidth)
    ) {
        Text(
            text = label,
            style = TypeTokens.LabelTextStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(containerColor)
                .padding(TypeTokens.ContainerPadding)
        )
    }
}

@Immutable
private object TypeTokens {

    val ContentColor: Color
        get() = Color.White

    @Composable
    fun getContainerColor(type: String): Color = when (type) {
        "normal"   -> Normal
        "fire"     -> Fire
        "electric" -> Electric
        "water"    -> Water
        "grass"    -> Grass
        "ice"      -> Ice
        "fighting" -> Fighting
        "poison"   -> Poison
        "ground"   -> Ground
        "flying"   -> Flying
        "psychic"  -> Psychic
        "bug"      -> Bug
        "rock"     -> Rock
        "ghost"    -> Ghost
        "dragon"   -> Dragon
        "dark"     -> Dark
        "steel"    -> Steel
        "fairy"    -> Fairy
        else       -> Normal
    }

    val LabelTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.bodySmall

    val ContainerShape: Shape
        get() = CircleShape

    val ContainerMinWidth: Dp
        get() = 87.dp

    val ContainerPadding: PaddingValues
        get() = PaddingValues(
            start = ContainerHorizontalPadding,
            top = ContainerVerticalPadding,
            end = ContainerHorizontalPadding,
            bottom = ContainerVerticalPadding
        )

    private val ContainerHorizontalPadding: Dp
        get() = 4.dp

    private val ContainerVerticalPadding: Dp
        get() = 4.dp

    val BorderStroke: BorderStroke
        get() = BorderStroke(
            width = BorderStrokeWidth,
            color = BorderStrokeColor
        )

    private val BorderStrokeWidth: Dp
        get() = 1.dp

    private val BorderStrokeColor: Color
        get() = Color.White
}

@Composable
@Preview
private fun TypePreview() {
    PokedexTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Type(label = "normal")
                Type(label = "fire")
                Type(label = "electric")
                Type(label = "water")
                Type(label = "grass")
                Type(label = "ice")
                Type(label = "fighting")
                Type(label = "poison")
                Type(label = "ground")
                Type(label = "flying")
                Type(label = "psychic")
                Type(label = "bug")
                Type(label = "rock")
                Type(label = "ghost")
                Type(label = "dragon")
                Type(label = "dark")
                Type(label = "steel")
                Type(label = "fairy")
            }
        }
    }
}