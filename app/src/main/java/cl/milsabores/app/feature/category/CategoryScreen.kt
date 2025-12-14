package cl.milsabores.app.feature.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.domain.model.Category
import cl.milsabores.app.core.domain.model.ProductsStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onDone: () -> Unit
) {
    val name = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var categoryToDelete by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {

            MilSaboresTopBar(
                onGoHome = onGoHome,
                onGoManage = onGoManage,
                onGoCart = onGoCart,
                onGoProfile = onGoProfile
            )

            Column(modifier = Modifier.padding(16.dp)) {

                // HEADER
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Categorías",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = onDone) { Text("Cerrar") }
                }

                Text(
                    text = "Administra las categorías de tus productos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(16.dp))

                // FORM
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Nombre de la categoría") },
                            isError = name.value.isBlank(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val text = name.value.trim()
                                val id = text.lowercase()
                                    .replace(" ", "_")
                                    .replace(Regex("[^a-z0-9_]"), "")

                                if (ProductsStore.categories.any { it.id == id }) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "La categoría ya existe",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    return@Button
                                }

                                ProductsStore.categories.add(Category(id, text))
                                name.value = ""

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Categoría agregada correctamente",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            enabled = name.value.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar categoría")
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Listado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                // LISTA CON ANIMACIÓN (SIN animateItemPlacement)
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(
                        ProductsStore.categories,
                        key = { it.id }
                    ) { category ->

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Row(
                                    Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = category.name,
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    IconButton(
                                        onClick = { categoryToDelete = category }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
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

        // DIALOG CONFIRMACIÓN
        categoryToDelete?.let { category ->
            AlertDialog(
                onDismissRequest = { categoryToDelete = null },
                confirmButton = {
                    TextButton(onClick = {
                        ProductsStore.products.removeAll {
                            it.categoryId == category.id
                        }
                        ProductsStore.categories.remove(category)
                        categoryToDelete = null

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Categoría eliminada",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { categoryToDelete = null }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Eliminar categoría") },
                text = {
                    Text(
                        "Esta acción eliminará la categoría y todos sus productos. ¿Deseas continuar?"
                    )
                }
            )
        }
    }
}
