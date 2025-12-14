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
        //Funcion que renderiza la pantalla inicial (en este caso el login)
        startDestination = Screen.Login.route,
        modifier = modifier.padding(innerPadding)
    ) {
        // HOME
        composable(Screen.Home.route) {
            HomeScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onGoToContact = { navController.navigate(Screen.Contact.route) },
                onGoProduct = { productId ->
                    navController.navigate("product/$productId")
                }
            )
        }

        // DETALLE PRODUCTO (sin navArgument)
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }

        // GESTIÓN
        composable(Screen.Manage.route) {
            ManageScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onAddProduct = { navController.navigate(Screen.Catalog.route) },
                onAddCategory = { navController.navigate(Screen.CategoryManage.route) },
                onAddAccount = { /* TODO */ }
            )
        }

        // CATALOGO / AGREGAR PRODUCTO
        composable(Screen.Catalog.route) {
            CatalogScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }

        // CATEGORÍAS
        composable(Screen.CategoryManage.route) {
            CategoryScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) },
                onDone = { navController.popBackStack() }
            )
        }

        // CARRITO
        composable(Screen.Cart.route) {
            CartScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // PERFIL
        composable(Screen.Profile.route) {
            ProfileScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // CONTACTO
        composable(Screen.Contact.route) {
            ContactScreen(
                onGoHome = { navController.navigate(Screen.Home.route) },
                onGoManage = { navController.navigate(Screen.Manage.route) },
                onGoCart = { navController.navigate(Screen.Cart.route) },
                onGoProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // LOGIN
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onGoRegister = { navController.navigate(Screen.Register.route) },
                onBack = { /* opcional: navController.popBackStack() */ }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { /* si quieres, directo a home */ },
                onGoLogin = { navController.popBackStack() }, // vuelve al login
                onBack = { navController.popBackStack() }
            )
        }

    }
}
