package dev.waylon.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.waylon.player.apis.common.util.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        Logger.i("MainActivity", "Application starting...")

        setContent {
            App()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.i("MainActivity", "Application shutting down...")
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}