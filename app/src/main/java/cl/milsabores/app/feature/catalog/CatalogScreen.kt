package cl.milsabores.app.feature.catalog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.domain.model.Product
import cl.milsabores.app.core.domain.model.ProductsStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import cl.milsabores.app.feature.catalog.components.CategoryDropdownSimple

@Composable
fun CatalogScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val categoryId = remember { mutableStateOf(ProductsStore.categories.first().id) }
    val price = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
            .padding(16.dp)
    ) {
        // HEADER + FORM
        item {
            MilSaboresTopBar(
                onGoHome = onGoHome,
                onGoManage = onGoManage,
                onGoCart = onGoCart,
                onGoProfile = onGoProfile
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Agregar producto",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .weight(1f)
                )

                TextButton(onClick = onDone) {
                    Text("Cerrar")
                }
            }

            Spacer(Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Nombre del producto") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    CategoryDropdownSimple(
                        selectedCategoryId = categoryId.value,
                        onCategorySelected = { selected ->
                            categoryId.value = selected
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = price.value,
                        onValueChange = { price.value = it },
                        label = { Text("Precio (ej. 40000)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = imageUrl.value,
                        onValueChange = { imageUrl.value = it },
                        label = { Text("URL de imagen") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val nameText = name.value.trim()
                            val priceInt = price.value.toIntOrNull()
                            val imageText = imageUrl.value.trim()

                            if (nameText.isEmpty() || priceInt == null || imageText.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Completa nombre, precio y URL",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            ProductsStore.products.add(
                                Product(
                                    id = (ProductsStore.products.size + 1).toString(),
                                    name = nameText,
                                    categoryId = categoryId.value,
                                    price = priceInt,
                                    imageUrl = imageText
                                )
                            )

                            name.value = ""
                            price.value = ""
                            imageUrl.value = ""
                            categoryId.value = ProductsStore.categories.first().id

                            Toast.makeText(
                                context,
                                "Producto guardado con éxito",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MarronBoton
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar producto")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Productos actuales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // LISTA PRODUCTOS
        items(ProductsStore.products, key = { it.id }) { product ->
            Card(
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Precio: $${product.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    IconButton(onClick = {
                        ProductsStore.products.remove(product)
                        Toast.makeText(
                            context,
                            "Producto eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar producto",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
