package dev.waylon.player

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.waylon.player.ui.screens.MainScreen
import dev.waylon.player.ui.theme.DarkColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = DarkColorScheme
    ) {
        MainScreen()
    }
}