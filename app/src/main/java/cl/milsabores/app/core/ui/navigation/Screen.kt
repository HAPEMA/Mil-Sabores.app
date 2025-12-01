sealed class Screen(val route: String, val label: String) {
    data object Home : Screen("home", "Inicio")
    data object Manage : Screen("manage", "Gestionar")
    data object Cart : Screen("cart", "Carrito")
    data object Profile : Screen("profile", "Perfil")

    data object Catalog : Screen("catalog", "AgregarProducto")
    data object CategoryManage : Screen("categories", "Categorías")
}
