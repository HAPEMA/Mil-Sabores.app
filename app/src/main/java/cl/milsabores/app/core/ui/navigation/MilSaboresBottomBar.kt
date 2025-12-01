package cl.milsabores.app.core.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MilSaboresBottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home to Icons.Filled.Home,
        Screen.Manage to Icons.Filled.List,
        Screen.Cart to Icons.Filled.ShoppingCart,
        Screen.Profile to Icons.Filled.Person
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { (screen, icon: ImageVector) ->
            val selected = currentDestination.isRouteInHierarchy(screen.route)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
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
