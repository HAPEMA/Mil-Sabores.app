// package cl.milsabores.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.core.ui.navigation.MilSaboresBottomBar
import cl.milsabores.app.core.ui.navigation.MilSaboresNavHost
import cl.milsabores.app.core.ui.navigation.Screen

@Composable
fun MilSaboresApp() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val routeActual = backStackEntry?.destination?.route

    val enAuth = routeActual == Screen.Login.route || routeActual == Screen.Register.route
    val logueado = SessionManager.currentUser != null

    MaterialTheme {
        Scaffold(
            topBar = { /* vacío */ },
            bottomBar = {
                if (logueado && !enAuth) {
                    MilSaboresBottomBar(navController = navController)
                }
            }
        ) { innerPadding ->
            MilSaboresNavHost(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}
