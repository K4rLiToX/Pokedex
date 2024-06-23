package com.carlosdiestro.features.pokemonEntriesListDetails.entries.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.carlosdiestro.design_system.theme.PokedexTheme

@Composable
fun PokemonEntry(
    onClick: (Color) -> Unit,
    name: String,
    order: String,
    coverUrl: String,
    modifier: Modifier = Modifier,
) {
    val defaultCardColors = PokemonEntryTokens.DefaultColors

    var cardColors by remember {
        mutableStateOf(defaultCardColors)
    }

    Column(
        modifier = modifier
            .clip(PokemonEntryTokens.ContainerShape)
            .clickable {
                onClick(cardColors.containerColor)
            }
            .verticalGradient(
                colors = listOf(
                    cardColors.containerColor.darken(),
                    cardColors.containerColor
                )
            )
            .width(PokemonEntryTokens.ContainerWidth)
    ) {
        Header(
            name = name,
            order = order,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PokemonEntryTokens.SpritePaddingValues)
        ) {
            AsyncImage(
                model = coverUrl,
                contentDescription = name,
                contentScale = ContentScale.Fit,
                onSuccess = {
                    it.result.drawable.let { drawable ->
                        calcContainerColor(drawable) { color ->
                            cardColors = cardColors.copy(
                                containerColor = color
                            )
                        }
                    }
                },
                modifier = Modifier
                    .size(PokemonEntryTokens.SpriteSize)
            )
        }
    }
}

@Composable
private fun Header(
    name: String,
    order: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(PokemonEntryTokens.HeaderPadding)
    ) {
        Text(
            text = name.replaceFirstChar { it.uppercase() },
            style = PokemonEntryTokens.TitleTextStyle,
            color = PokemonEntryTokens.ContentColor
        )
        Text(
            text = order,
            style = PokemonEntryTokens.SubtitleTextStyle,
            color = PokemonEntryTokens.ContentColor
        )
    }
}

@Immutable
private object PokemonEntryTokens {

    val DefaultColors: CardColors
        @Composable
        get() = CardDefaults.cardColors()

    val ContentColor: Color
        get() = Color.White

    val TitleTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.titleSmall

    val SubtitleTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.bodySmall

    val ContainerWidth: Dp
        get() = 180.dp

    val ContainerShape: RoundedCornerShape
        get() = RoundedCornerShape(24.dp)

    val SpriteSize: Dp
        get() = 96.dp

    val SpritePaddingValues: PaddingValues
        get() = PaddingValues(
            end = SpriteHorizontalPadding,
            bottom = SpriteVerticalPadding
        )

    private val SpriteHorizontalPadding: Dp
        get() = 16.dp

    private val SpriteVerticalPadding: Dp
        get() = 16.dp

    val HeaderPadding: PaddingValues
        get() = PaddingValues(
            start = HeaderHorizontalPadding,
            top = HeaderVerticalPadding,
            bottom = HeaderVerticalPadding
        )

    private val HeaderHorizontalPadding: Dp
        get() = 16.dp

    private val HeaderVerticalPadding: Dp
        get() = 16.dp
}

private fun calcContainerColor(drawable: Drawable, onFinish: (Color) -> Unit) {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    Palette.from(bmp).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { value ->
            onFinish(Color(value))
        }
    }
}

private fun Color.darken(factor: Float = 0.5F): Color {
    val darker = ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), factor)
    return Color(darker)
}

private fun Modifier.verticalGradient(colors: List<Color>): Modifier = this.then(
    Modifier.drawWithContent {
        val gradient = Brush.verticalGradient(colors)
        drawRect(gradient)
        drawContent()
    }
)

@Composable
@PreviewLightDark
private fun PokemonEntryPreview() {
    PokedexTheme {
        Surface {
            PokemonEntry(
                onClick = {},
                name = "Bulbasaur",
                order = "#001",
                coverUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            )
        }
    }
}