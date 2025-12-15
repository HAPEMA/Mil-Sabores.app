package cl.milsabores.app.core.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.feature.admin.ManageScreen
import cl.milsabores.app.feature.cart.CartScreen
import cl.milsabores.app.feature.catalog.CatalogScreen
import cl.milsabores.app.feature.category.CategoryScreen
import cl.milsabores.app.feature.home.HomeScreen
import cl.milsabores.app.feature.profile.ProfileScreen
import cl.milsabores.app.feature.contact.ContactScreen
import cl.milsabores.app.feature.auth.login.LoginScreen
import cl.milsabores.app.feature.auth.register.RegisterScreen
import cl.milsabores.app.feature.product.ProductDetailScreen

@Composable
fun MilSaboresNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier.padding(innerPadding)
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onGoRegister = { navController.navigate(Screen.Register.route) },
                onBack = { }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() },
                onGoLogin = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onGoToContact = { navController.navigate(Screen.Contact.route) },
                onGoProduct = { productId -> navController.navigate("product/$productId") }
            )
        }

        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true } // o popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Contact.route) {
            ContactScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Manage.route) {
            ManageScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onAddProduct = { navController.navigate(Screen.Catalog.route) },
                onAddCategory = { navController.navigate(Screen.CategoryManage.route) },
                onAddAccount = { }
            )
        }

        composable(Screen.Catalog.route) {
            CatalogScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }

        composable(Screen.CategoryManage.route) {
            CategoryScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }
    }
}

