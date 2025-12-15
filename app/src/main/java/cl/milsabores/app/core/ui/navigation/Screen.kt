package cl.milsabores.app.core.ui.navigation

sealed class Screen(val route: String, val label: String) {
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Register")

    object Home : Screen("home", "Inicio")
    object Manage : Screen("manage", "Gestionar")
    object Cart : Screen("cart", "Carrito")
    object Profile : Screen("profile", "Perfil")

    object Contact : Screen("contact", "Contacto")
    object Catalog : Screen("catalog", "Catálogo")
    object CategoryManage : Screen("category", "Categorías")
}
