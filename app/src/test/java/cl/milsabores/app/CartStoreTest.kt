package cl.milsabores.app

import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.domain.model.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartStoreTest {

    @Before
    fun setUp() {
        CartStore.clear()
    }

    private fun product(
        id: String = "1",
        name: String = "Torta Chocolate",
        categoryId: String = "postres",
        price: Int = 10000,
        imageUrl: String = ""
    ) = Product(
        id = id,
        name = name,
        categoryId = categoryId,
        price = price,
        imageUrl = imageUrl
    )

    @Test
    fun agregarProducto_calculaTotalCorrecto() {
        val p = product(price = 10000)

        CartStore.addToCart(p)

        assertEquals(10000, CartStore.getTotal())
        assertEquals(1, CartStore.items.size)
        assertEquals(1, CartStore.items[0].quantity)
    }

    @Test
    fun agregarMismoProducto_incrementaCantidad() {
        val p = product(id = "1", price = 5000)

        CartStore.addToCart(p)
        CartStore.addToCart(p)

        assertEquals(1, CartStore.items.size)
        assertEquals(2, CartStore.items[0].quantity)
        assertEquals(10000, CartStore.getTotal())
    }

    @Test
    fun eliminarProducto_quitaDelCarritoYActualizaTotal() {
        val p1 = product(id = "1", price = 7000)
        val p2 = product(id = "2", name = "Cheesecake", price = 8000)

        CartStore.addToCart(p1)
        CartStore.addToCart(p2)

        CartStore.removeItem("1")

        assertEquals(1, CartStore.items.size)
        assertTrue(CartStore.items.none { it.product.id == "1" })
        assertEquals(8000, CartStore.getTotal())
    }
}
