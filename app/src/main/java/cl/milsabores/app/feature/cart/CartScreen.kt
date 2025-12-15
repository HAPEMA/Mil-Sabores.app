package cl.milsabores.app.feature.cart

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import cl.milsabores.app.core.data.local.DatabaseProvider
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit
) {
    val context = LocalContext.current
    var showCheckoutDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Mi carrito de compras",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    AnimatedContent(
                        targetState = CartStore.items.size,
                        transitionSpec = {
                            slideInVertically { it } + fadeIn() togetherWith
                                    slideOutVertically { -it } + fadeOut()
                        },
                        label = "item_count"
                    ) { count ->
                        Text(
                            text = "$count ${if (count == 1) "producto" else "productos"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // Icono carrito + badge
                Box {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = MarronBoton,
                        modifier = Modifier.size(32.dp)
                    )

                    this@Row.AnimatedVisibility(
                        visible = CartStore.items.isNotEmpty(),
                        enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.error,
                            modifier = Modifier.offset(x = 4.dp, y = (-4).dp)
                        ) {
                            Text(
                                text = CartStore.items.size.toString(),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (CartStore.items.isEmpty()) {
                EmptyCartView()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(
                        items = CartStore.items.toList(),
                        key = { _, item -> item.product.id }
                    ) { index, item ->
                        AnimatedCartItemCard(
                            item = item,
                            index = index
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                AnimatedSummaryCard(
                    onCheckout = { showCheckoutDialog = true }
                )
            }
        }
    }

    // ✅ Dialog checkout (fuera del Column principal)
    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MarronBoton
                )
            },
            title = { Text("Confirmar Compra", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("¿Deseas completar tu compra?")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Total: $${CartStore.getTotal()}",
                        fontWeight = FontWeight.Bold,
                        color = MarronBoton
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            val user = SessionManager.currentUser
                            if (user == null) {
                                Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
                                return@launch
                            }

                            try {
                                val db = DatabaseProvider.get(context)
                                val compraDao = db.compraDao()

                                val total = CartStore.getTotal()

                                // 1) Insert compra
                                val compraId = compraDao.insertCompra(
                                    CompraEntity(
                                        usuarioId = user.id,
                                        total = total
                                    )
                                )

                                // 2) Insert detalles
                                val detalles = CartStore.items.map { ci ->
                                    DetalleCompraEntity(
                                        compraId = compraId,
                                        productoId = ci.product.id,
                                        nombre = ci.product.name,
                                        precio = ci.product.price,
                                        cantidad = ci.quantity,
                                        fotoUrl = ci.product.imageUrl
                                    )
                                }
                                compraDao.insertDetalles(detalles)

                                CartStore.clear()
                                showCheckoutDialog = false
                                Toast.makeText(context, "¡Compra guardada! ✅", Toast.LENGTH_LONG).show()

                            } catch (e: Exception) {
                                Toast.makeText(context, "Error guardando compra: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MarronBoton)
                ) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showCheckoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun AnimatedCartItemCard(
    item: CartStore.CartItem,
    index: Int
) {
    var visible by remember { mutableStateOf(false) }
    var isRemoving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 50L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible && !isRemoving,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300)
        ) + fadeOut() + shrinkVertically()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.product.imageUrl,
                    contentDescription = item.product.name,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF5F5F5))
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "$${item.product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MarronBoton
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantitySelector(
                            quantity = item.quantity,
                            onIncrease = {
                                CartStore.updateQuantity(item.product.id, item.quantity + 1)
                            },
                            onDecrease = {
                                if (item.quantity > 1) {
                                    CartStore.updateQuantity(item.product.id, item.quantity - 1)
                                }
                            }
                        )

                        Spacer(Modifier.weight(1f))

                        var deletePressed by remember { mutableStateOf(false) }
                        val deleteScale by animateFloatAsState(
                            targetValue = if (deletePressed) 0.85f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "deleteScale"
                        )

                        IconButton(
                            onClick = {
                                isRemoving = true
                                CartStore.removeItem(item.product.id)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(deleteScale)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            deletePressed = true
                                            tryAwaitRelease()
                                            deletePressed = false
                                        }
                                    )
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButton(
            onClick = {
                onDecrease()
                scope.launch {
                    scale.animateTo(0.8f, tween(100))
                    scale.animateTo(1f, tween(100))
                }
            },
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFFF5F5F5), CircleShape)
        ) {
            Text("−", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MarronBoton)
        }

        Text(
            text = quantity.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.scale(scale.value).padding(horizontal = 12.dp)
        )

        IconButton(
            onClick = {
                onIncrease()
                scope.launch {
                    scale.animateTo(1.2f, tween(100))
                    scale.animateTo(1f, tween(100))
                }
            },
            modifier = Modifier
                .size(32.dp)
                .background(MarronBoton, CircleShape)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Aumentar", tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun AnimatedSummaryCard(onCheckout: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Resumen de la compra", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total de productos:", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = CartStore.items.sumOf { it.quantity }.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total a pagar:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "$${CartStore.getTotal()}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MarronBoton
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                colors = ButtonDefaults.buttonColors(MarronBoton),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Proceder al Pago", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun EmptyCartView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = Color(0xFFBDBDBD),
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text("Tu carrito está vacío", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9E9E9E))
        Spacer(Modifier.height(8.dp))
        Text("Agrega productos para comenzar", fontSize = 14.sp, color = Color(0xFFBDBDBD))
    }
}
