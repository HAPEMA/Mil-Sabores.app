package cl.milsabores.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.milsabores.app.core.ui.theme.MilSaboresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MilSaboresTheme {
                MilSaboresApp()
            }
        }
    }
}
