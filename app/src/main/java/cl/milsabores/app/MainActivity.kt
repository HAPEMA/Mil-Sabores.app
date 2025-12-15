package cl.milsabores.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import cl.milsabores.app.core.ui.navigation.MilSaboresBottomBar
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { MilSaboresBottomBar(navController) }
                ) { innerPadding ->
                    MilSaboresNavHost(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

