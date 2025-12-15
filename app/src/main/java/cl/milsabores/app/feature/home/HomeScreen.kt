package cl.milsabores.app.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.R
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.domain.model.ProductsStore
import cl.milsabores.app.core.ui.theme.CremaFondo
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onGoToContact: () -> Unit,
    onGoProduct: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = CremaFondo
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(CremaFondo)
                .verticalScroll(rememberScrollState())
        ) {

            HeroBanner(onContactClick = onGoToContact)

            Spacer(Modifier.height(24.dp))

            ProductsSection(
                onGoProduct = onGoProduct,
                onAddToCart = { product ->
                    CartStore.addToCart(product)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Producto agregado al carrito 🛒",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun HeroBanner(onContactClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.65f),
                            Color.Black.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Mil Sabores",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Repostería artesanal\npara momentos especiales",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Tortas, pasteles y dulces elaborados con ingredientes de calidad y dedicación.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }

            Button(
                onClick = onContactClick,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(48.dp)
            ) {
                Text("Contáctanos", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun ProductsSection(
    onGoProduct: (String) -> Unit,
    onAddToCart: (product: cl.milsabores.app.core.domain.model.Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Nuestros Productos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Explora nuestra selección de productos destacados.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(20.dp))

        if (ProductsStore.products.isEmpty()) {
            Text(
                text = "Aún no hay productos disponibles.",
                style = MaterialTheme.typography.bodyMedium
            )
            return
        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ProductsStore.products.forEach { product ->

                val categoryName =
                    ProductsStore.categories.firstOrNull { it.id == product.categoryId }?.name
                        ?: "Sin categoría"

                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        ) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = categoryName.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = "$${product.price}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = { onAddToCart(product) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Agregar al carrito")
                            }

                            TextButton(
                                onClick = { onGoProduct(product.id) },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Ver detalle")
                            }
                        }
                    }
                }
            }
        }
    }
}
