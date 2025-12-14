package cl.milsabores.app.feature.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.domain.model.ProductsStore
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit
) {
    val product = ProductsStore.products.firstOrNull { it.id == productId }
    val categoryName = ProductsStore.categories
        .firstOrNull { it.id == product?.categoryId }
        ?.name ?: "Sin categoría"

    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado")
        }
        return
    }

    // 🔔 Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AddToCartBar(
                price = product.price,
                onAddToCart = {
                    CartStore.addToCart(product)

                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Producto agregado al carrito 🛒",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Box {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = categoryName.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Producto artesanal preparado con ingredientes de alta calidad. Ideal para celebraciones o para darte un gusto.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(80.dp)) // espacio para el bottom bar
            }
        }
    }
}

@Composable
fun AddToCartBar(
    price: Int,
    onAddToCart: () -> Unit
) {
    Surface(
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "$${price}",
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = onAddToCart,
                modifier = Modifier.height(48.dp)
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
