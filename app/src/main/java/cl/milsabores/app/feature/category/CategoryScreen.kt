package cl.milsabores.app.feature.category

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import cl.milsabores.app.core.domain.model.ProductsStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import cl.milsabores.app.core.domain.model.Category

@Composable
fun CategoryScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onDone: () -> Unit
) {
    val name = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
    ) {
        // HEADER GLOBAL
        MilSaboresTopBar(
            onGoHome = onGoHome,
            onGoManage = onGoManage,
            onGoCart = onGoCart,
            onGoProfile = onGoProfile
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Categorías",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = onDone) {
                    Text("Cerrar")
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Administra las categorías de tus productos.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // FORMULARIO
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Nombre de la categoría") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val text = name.value.trim()
                            if (text.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Escribe un nombre de categoría",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val id = text.lowercase()
                                .replace(" ", "_")
                                .replace(Regex("[^a-z0-9_]"), "")

                            val exists = ProductsStore.categories.any { it.id == id }
                            if (exists) {
                                Toast.makeText(
                                    context,
                                    "Ya existe una categoría con ese nombre",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                ProductsStore.categories.add(
                                    Category(
                                        id = id,
                                        name = text
                                    )
                                )
                                name.value = ""
                                Toast.makeText(
                                    context,
                                    "Categoría agregada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MarronBoton
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar categoría")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Listado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // LISTA + ELIMINAR
            LazyColumn {
                items(ProductsStore.categories, key = { it.id }) { category ->
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
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(onClick = {
                                ProductsStore.products.removeAll { it.categoryId == category.id }
                                ProductsStore.categories.remove(category)

                                Toast.makeText(
                                    context,
                                    "Categoría eliminada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar categoría",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
