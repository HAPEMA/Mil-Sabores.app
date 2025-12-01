package cl.milsabores.app.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton

@Composable
fun CartScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
    ) {

        MilSaboresTopBar(
            onGoHome = onGoHome,
            onGoManage = onGoManage,
            onGoCart = onGoCart,
            onGoProfile = onGoProfile
        )

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Mi carrito de compras",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            if (CartStore.items.isEmpty()) {
                Text("No tienes productos aún.")
                return
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(CartStore.items) { item ->
                    CartItemCard(item)
                }
            }

            Spacer(Modifier.height(16.dp))

            // TOTAL
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Resumen de la compra",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Total de productos: ${CartStore.items.sumOf { it.quantity }}")
                    Text("Total a pagar: $${CartStore.getTotal()}")

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { /* TODO: Pago */ },
                        colors = ButtonDefaults.buttonColors(MarronBoton),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Comprar")
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartStore.CartItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.product.imageUrl,
                contentDescription = item.product.name,
                modifier = Modifier
                    .size(80.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = item.product.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Precio: $${item.product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Cantidad: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = {
                CartStore.removeItem(item.product.id)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
