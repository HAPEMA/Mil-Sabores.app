package cl.milsabores.app.core.domain.model

import androidx.compose.runtime.mutableStateListOf

/**
 * CartStore sigue el principio SRP: solo gestiona carrito.
 * Los modelos son inmutables para asegurar recomposición en Compose.
 */
object CartStore {

    /**
     * CartItem es INMUTABLE. Esto garantiza que cualquier cambio
     * sea detectado por Compose (cambia la referencia completa).
     */
    data class CartItem(
        val product: Product,
        val quantity: Int
    )

    // Lista observable por Compose
    val items = mutableStateListOf<CartItem>()

    /**
     * Agrega un producto o incrementa su cantidad.
     * (Open/Closed: no quiebras nada si extiendes reglas de carrito)
     */
    fun addToCart(product: Product) {
        val index = items.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            val existing = items[index]
            items[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            items.add(CartItem(product = product, quantity = 1))
        }
    }

    /**
     * Actualiza cantidad de forma segura y observable.
     * (LSP: copy mantiene contrato del modelo sin efectos secundarios)
     */
    fun updateQuantity(productId: String, newQuantity: Int) {
        val index = items.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            val old = items[index]
            items[index] = old.copy(quantity = newQuantity)
        }
    }

    /**
     * Elimina un item por id.
     * (Interface Segregation: no mezclas comportamientos innecesarios)
     */
    fun removeItem(productId: String) {
        items.removeAll { it.product.id == productId }
    }

    /**
     * Limpia todo el carrito.
     */
    fun clear() {
        items.clear()
    }

    /**
     * Calcula el total.
     * (Dependency Inversion: UI depende de esta abstracción)
     */
    fun getTotal(): Int {
        return items.sumOf { it.product.price * it.quantity }
    }
}
