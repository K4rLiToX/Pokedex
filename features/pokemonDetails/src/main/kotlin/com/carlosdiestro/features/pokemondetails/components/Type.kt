package com.carlosdiestro.features.pokemondetails.components

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
        "Normal"   -> Normal
        "Fire"     -> Fire
        "Electric" -> Electric
        "Water"    -> Water
        "Grass"    -> Grass
        "Ice"      -> Ice
        "Fighting" -> Fighting
        "Poison"   -> Poison
        "Ground"   -> Ground
        "Flying"   -> Flying
        "Psychic"  -> Psychic
        "Bug"      -> Bug
        "Rock"     -> Rock
        "Ghost"    -> Ghost
        "Dragon"   -> Dragon
        "Dark"     -> Dark
        "Steel"    -> Steel
        "Fairy"    -> Fairy
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
}

@Composable
@Preview
private fun TypePreview() {
    PokedexTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Type(label = "Normal")
                Type(label = "Fire")
                Type(label = "Electric")
                Type(label = "Water")
                Type(label = "Grass")
                Type(label = "Ice")
                Type(label = "Fighting")
                Type(label = "Poison")
                Type(label = "Ground")
                Type(label = "Flying")
                Type(label = "Psychic")
                Type(label = "Bug")
                Type(label = "Rock")
                Type(label = "Ghost")
                Type(label = "Dragon")
                Type(label = "Dark")
                Type(label = "Steel")
                Type(label = "Fairy")
            }
        }
    }
}