package cl.milsabores.app.feature.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.core.domain.model.ProductsStore

@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit
) {
    val product = ProductsStore.products.firstOrNull { it.id == productId }

    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Volver")
        }

        Spacer(Modifier.height(16.dp))

        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(product.name, style = MaterialTheme.typography.headlineSmall)
        Text("Precio: $${product.price}", style = MaterialTheme.typography.titleMedium)
        Text(
            "Categoría: ${
                ProductsStore.categories.firstOrNull { it.id == product.categoryId }?.name
                    ?: "Sin categoría"
            }",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
