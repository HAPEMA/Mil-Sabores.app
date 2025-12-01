package cl.milsabores.app.core.domain.model

data class Category(
    val id: String,
    val name: String
)

data class Product(
    val id: String,
    val name: String,
    val categoryId: String,
    val price: Int,
    val imageUrl: String
)
