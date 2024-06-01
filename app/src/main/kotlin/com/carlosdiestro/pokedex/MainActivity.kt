package com.carlosdiestro.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.carlosdiestro.design_system.theme.PokedexTheme
import com.carlosdiestro.pokedex.ui.PokedexApp
import com.carlosdiestro.pokedex.ui.rememberPokedexAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberPokedexAppState()

            PokedexTheme {
                PokedexApp(
                    appState = appState,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}