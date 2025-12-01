package cl.milsabores.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cl.milsabores.app.core.ui.navigation.MilSaboresBottomBar
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost

@Composable
fun MilSaboresApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MilSaboresBottomBar(navController = navController)
        }
    ) { innerPadding: PaddingValues ->
        MilSaboresNavHost(
            navController = navController,
            modifier = Modifier,
            innerPadding = innerPadding
        )
    }
}
