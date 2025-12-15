package cl.milsabores.app.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import cl.milsabores.app.core.ui.navigation.MilSaboresBottomBar
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost

@Composable
fun MilSaboresApp() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = { /* vacío */ },
            bottomBar = { MilSaboresBottomBar(navController) }
        ) { innerPadding ->
            MilSaboresNavHost(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}
