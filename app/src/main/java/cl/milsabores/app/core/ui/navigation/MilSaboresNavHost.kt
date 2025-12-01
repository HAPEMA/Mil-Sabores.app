package cl.milsabores.app.core.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.milsabores.app.feature.admin.ManageScreen
import cl.milsabores.app.feature.cart.CartScreen
import cl.milsabores.app.feature.catalog.CatalogScreen
import cl.milsabores.app.feature.category.CategoryScreen
import cl.milsabores.app.feature.home.HomeScreen
import cl.milsabores.app.feature.profile.ProfileScreen
import cl.milsabores.app.feature.profile_edit.EditProfileScreen

@Composable
fun MilSaboresNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier.padding(innerPadding)
    ) {

        // HOME
        composable(Screen.Home.route) {
            HomeScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onGoToContact = {}
            )
        }

        // MANAGE
        composable(Screen.Manage.route) {
            ManageScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onAddProduct = { navController.navigate(Screen.Catalog.route) },
                onAddCategory = { navController.navigate(Screen.CategoryManage.route) },
                onAddAccount = {}
            )
        }

        // CATALOG
        composable(Screen.Catalog.route) {
            CatalogScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }

        // CATEGORIES
        composable(Screen.CategoryManage.route) {
            CategoryScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }

        // CART
        composable(Screen.Cart.route) {
            CartScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // PROFILE
        composable(Screen.Profile.route) {
            ProfileScreen(
                onGoHome = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onLogout = {},
                onEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                }
            )
        }

        // EDIT PROFILE
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onGoBack = { navController.popBackStack() }
            )
        }
    }
}
