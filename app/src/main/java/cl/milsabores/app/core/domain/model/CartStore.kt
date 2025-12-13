package cl.milsabores.app.core.domain.model

import androidx.compose.runtime.mutableStateListOf

object CartStore {

    data class CartItem(
        val product: Product,
        val quantity: Int
    )

    // Lista
    val items = mutableStateListOf<CartItem>()

    //  Agrega un producto o incrementa su cantidad.

    fun addToCart(product: Product) {
        val index = items.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            val existing = items[index]
            items[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            items.add(CartItem(product = product, quantity = 1))
        }
    }

    // Actualiza cantidad
    fun updateQuantity(productId: String, newQuantity: Int) {
        val index = items.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            val old = items[index]
            items[index] = old.copy(quantity = newQuantity)
        }
    }

    // Elimina un item por id.
    fun removeItem(productId: String) {
        items.removeAll { it.product.id == productId }
    }

    //  Limpiar carrito

    fun clear() {
        items.clear()
    }

    // Calcula el total.

    fun getTotal(): Int {
        return items.sumOf { it.product.price * it.quantity }
    }
}