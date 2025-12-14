package cl.milsabores.app.feature.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.R
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.domain.model.ProductsStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton

@Composable
fun HomeScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onGoToContact: () -> Unit,
    onGoProduct: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
            .verticalScroll(rememberScrollState())
    ) {

        MilSaboresTopBar(
            onGoHome = onGoHome,
            onGoManage = onGoManage,
            onGoCart = onGoCart,
            onGoProfile = onGoProfile
        )

        HeroBanner(onContactClick = onGoToContact)

        Spacer(Modifier.height(24.dp))

        ProductsSection(
            onGoProduct = onGoProduct
        )

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun HeroBanner(
    onContactClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
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
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Buscamos ofrecer una\nexperiencia de compra moderna.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Te ofrecemos una experiencia dulce y memorable, " +
                            "proporcionando tortas y productos de repostería de alta calidad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }

            Button(
                onClick = onContactClick,
                colors = ButtonDefaults.buttonColors(containerColor = MarronBoton)
            ) {
                Text("Contáctanos")
            }
        }
    }
}

@Composable
private fun ProductsSection(
    onGoProduct: (String) -> Unit
) {
    val context = LocalContext.current

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

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Explora nuestra selección de productos destacados.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(16.dp))

        if (ProductsStore.products.isEmpty()) {
            Text("Aún no hay productos.")
            return
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProductsStore.products.forEach { product ->

                val categoryName =
                    ProductsStore.categories
                        .firstOrNull { it.id == product.categoryId }
                        ?.name ?: "Sin categoría"

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        ) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.25f))
                                    .align(Alignment.BottomStart)
                                    .padding(6.dp)
                            ) {
                                Text(
                                    text = product.name,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = categoryName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MarronBoton
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Precio: $${product.price}",
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                CartStore.addToCart(product)
                                Toast.makeText(
                                    context,
                                    "Producto agregado al carrito",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MarronBoton),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar al carrito")
                        }

                        Spacer(Modifier.height(6.dp))

                        Button(
                            onClick = { onGoProduct(product.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = MarronBoton),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver producto")
                        }
                    }
                }
            }
        }
    }
}
