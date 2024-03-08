package com.carlosdiestro.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.carlosdiestro.features.home.RegionsUiState
import com.carlosdiestro.features.home.models.RegionPlo

@Composable
internal fun rememberRegionsDialogState(): RegionsDialogState = remember {
    RegionsDialogState()
}

@Stable
class RegionsDialogState {
    var canOpenRegionsDialog by mutableStateOf(false)

    fun openRegionsDialog() {
        canOpenRegionsDialog = true
    }

    fun closeRegionsDialog() {
        canOpenRegionsDialog = false
    }
}

@Composable
internal fun RegionsDialog(
    state: RegionsUiState,
    onDismiss: () -> Unit,
    onRegionClick: (RegionPlo) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Text(text = "Choose a region")
        },
        text = {
            when (state) {
                is RegionsUiState.Error -> Error(message = state.message)

                RegionsUiState.Loading -> Loading()

                is RegionsUiState.Success -> RegionList(
                    regions = state.regions,
                    onDismiss = onDismiss,
                    onRegionClick = onRegionClick
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun RegionList(
    regions: List<RegionPlo>,
    onDismiss: () -> Unit,
    onRegionClick: (RegionPlo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .wrapContentHeight()
    ) {
        items(
            items = regions,
            key = { region -> region.id }
        ) { region ->
            RegionItem(
                region = region,
                onClick = {
                    onRegionClick(region)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
private fun RegionItem(
    region: RegionPlo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(
                horizontal = 4.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = region.name,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(
    message: String?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(text = message ?: "Message not found")
    }
}