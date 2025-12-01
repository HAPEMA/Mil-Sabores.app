package cl.milsabores.app.core.domain.model

import androidx.compose.runtime.mutableStateListOf

object CartStore {

    data class CartItem(
        val product: Product,
        var quantity: Int
    )

    val items = mutableStateListOf<CartItem>()

    fun addToCart(product: Product) {
        val existing = items.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += 1
        } else {
            items.add(CartItem(product, 1))
        }
    }

    fun removeItem(productId: String) {
        items.removeAll { it.product.id == productId }
    }

    fun clear() = items.clear()

    fun getTotal() = items.sumOf { it.product.price * it.quantity }
}
