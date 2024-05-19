package com.carlosdiestro.design_system.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.carlosdiestro.design_system.R

@Immutable
object PokedexIcons {

    val Abilities: Int
        @DrawableRes
        get() = R.drawable.ic_abilities

    val Back: ImageVector
        get() = Icons.AutoMirrored.Rounded.ArrowBack

    val ChevronDown: ImageVector
        get() = Icons.Rounded.KeyboardArrowDown

    val Clear: ImageVector
        get() = Icons.Rounded.Clear

    val Group: Int
        @DrawableRes
        get() = R.drawable.ic_groups

    val Height: Int
        @DrawableRes
        get() = R.drawable.ic_height

    val History: Int
        @DrawableRes
        get() = R.drawable.ic_history

    val Search: ImageVector
        get() = Icons.Rounded.Search

    val Settings: ImageVector
        get() = Icons.Outlined.Settings

    val Weight: Int
        @DrawableRes
        get() = R.drawable.ic_weight
}