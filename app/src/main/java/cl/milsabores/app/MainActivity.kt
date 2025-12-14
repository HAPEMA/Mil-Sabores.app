package cl.milsabores.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    MilSaboresNavHost(
                        navController = navController,
                        innerPadding = androidx.compose.foundation.layout.PaddingValues()
                    )
                }
            }
        }
    }
}
