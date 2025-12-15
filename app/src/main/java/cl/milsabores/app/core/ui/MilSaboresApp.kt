package cl.milsabores.app.core.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.milsabores.app.core.ui.navigation.MilSaboresBottomBar
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost
import cl.milsabores.app.core.ui.navigation.Screen

@Composable
fun MilSaboresApp() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route

    // 👇 Ocultar bottomBar en login/register
    val showBottomBar = currentRoute !in listOf(
        Screen.Login.route,
        Screen.Register.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MilSaboresBottomBar(navController)
            }
        }
    ) { innerPadding ->
        MilSaboresNavHost(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}
