package com.carlosdiestro.features.pokemonEntriesListDetails.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carlosdiestro.design_system.theme.PokedexTheme

@Composable
fun Stat(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(StatTokens.ItemSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(StatTokens.ContainerPadding)
    ) {
        Text(
            text = label,
            style = StatTokens.LabelTextStyle,
            modifier = Modifier
                .requiredSizeIn(minWidth = StatTokens.LabelMinWidth)
        )
        Text(
            text = value.toString(),
            style = StatTokens.ValueTextStyle,
            modifier = Modifier
                .requiredSizeIn(minWidth = StatTokens.ValueMinWidth)
        )
        VisualStat(
            value = value,
            modifier = Modifier
                .weight(1F)
        )
    }
}

@Composable
private fun VisualStat(
    value: Int,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        progress = {
            value / 100F
        },
        strokeCap = StrokeCap.Round,
        modifier = modifier
            .requiredHeight(StatTokens.VisualStatHeight)
    )
}

@Immutable
private object StatTokens {

    val LabelTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.bodySmall

    val ValueTextStyle: TextStyle
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography.bodyMedium

    val ItemSpacing: Dp
        get() = 24.dp

    val LabelMinWidth: Dp
        get() = 50.dp

    val ValueMinWidth: Dp
        get() = 50.dp

    val VisualStatHeight: Dp
        get() = 20.dp

    val ContainerPadding: PaddingValues
        get() = PaddingValues(
            top = ContainerVerticalPadding,
            bottom = ContainerVerticalPadding
        )

    private val ContainerVerticalPadding: Dp
        get() = 12.dp
}

@Composable
@PreviewLightDark
private fun StatPreview() {
    PokedexTheme {
        Surface {
            Stat(
                label = "HP",
                value = 45
            )
        }
    }
}