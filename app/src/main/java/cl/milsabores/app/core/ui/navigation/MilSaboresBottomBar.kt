package cl.milsabores.app.core.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cl.milsabores.app.core.domain.session.SessionManager
@Composable
fun MilSaboresBottomBar(navController: NavHostController) {

    val items = buildList {
        add(Screen.Home to Icons.Filled.Home)
        if (SessionManager.isAdmin) add(Screen.Manage to Icons.Filled.List)
        add(Screen.Cart to Icons.Filled.ShoppingCart)
        add(Screen.Profile to Icons.Filled.Person)
    }

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { (screen, icon) ->
            val selected = currentDestination.isRouteInHierarchy(screen.route)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: String): Boolean =
    this?.hierarchy?.any { it.route == route } == true
