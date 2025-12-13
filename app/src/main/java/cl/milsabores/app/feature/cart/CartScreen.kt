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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
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
import cl.milsabores.app.core.domain.model.CartStore
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
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
        MilSaboresTopBar(
            onGoHome = onGoHome,
            onGoManage = onGoManage,
            onGoCart = onGoCart,
            onGoProfile = onGoProfile
        )

        Column(modifier = Modifier.padding(16.dp)) {

            // Header animado
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

                // Icono del carrito con badge animado
                Box {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = MarronBoton,
                        modifier = Modifier.size(32.dp)
                    )

                    androidx.compose.animation.AnimatedVisibility(
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
                // Lista con animaciones
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(
                        items = CartStore.items.toList(), // Forzar recomposición
                        key = { _, item -> item.product.id }
                    ) { index, item ->
                        AnimatedCartItemCard(
                            item = item,
                            index = index,
                            onUpdateQuantity = { } // Ya no se usa, se maneja directo en el componente
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Resumen con animación
                AnimatedSummaryCard(
                    onCheckout = { showCheckoutDialog = true }
                )
            }
        }
    }

    // Diálogo de confirmación
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
            title = {
                Text(
                    text = "Confirmar Compra",
                    fontWeight = FontWeight.Bold
                )
            },
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
                        showCheckoutDialog = false
                        Toast.makeText(
                            context,
                            "¡Compra realizada con éxito! 🎉",
                            Toast.LENGTH_LONG
                        ).show()
                        CartStore.clear()
                    },
                    colors = ButtonDefaults.buttonColors(MarronBoton)
                ) {
                    Text("Confirmar")
                }
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
fun AnimatedCartItemCard(
    item: CartStore.CartItem,
    index: Int,
    onUpdateQuantity: (Int) -> Unit
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
                // Imagen con esquinas redondeadas
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
                        text = "${item.product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MarronBoton
                    )

                    Spacer(Modifier.height(8.dp))

                    // Selector de cantidad animado
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantitySelector(
                            quantity = item.quantity,
                            onIncrease = {
                                val currentItem = CartStore.items.find { it.product.id == item.product.id }
                                currentItem?.let {
                                    CartStore.updateQuantity(item.product.id, it.quantity + 1)
                                }
                            },
                            onDecrease = {
                                val currentItem = CartStore.items.find { it.product.id == item.product.id }
                                currentItem?.let {
                                    if (it.quantity > 1) {
                                        CartStore.updateQuantity(item.product.id, it.quantity - 1)
                                    }
                                }
                            }
                        )

                        Spacer(Modifier.weight(1f))

                        // Botón eliminar con animación
                        var deletePressed by remember { mutableStateOf(false) }
                        val deleteScale by animateFloatAsState(
                            targetValue = if (deletePressed) 0.85f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
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
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
            Text(
                text = "−",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MarronBoton
            )
        }

        AnimatedContent(
            targetState = quantity,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()
                } else {
                    slideInVertically { -it } + fadeIn() togetherWith
                            slideOutVertically { it } + fadeOut()
                }
            },
            label = "quantity"
        ) { count ->
            Text(
                text = count.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .scale(scale.value)
                    .padding(horizontal = 12.dp)
            )
        }

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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun AnimatedSummaryCard(onCheckout: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Resumen de la compra",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total de productos:",
                    style = MaterialTheme.typography.bodyLarge
                )
                AnimatedContent(
                    targetState = CartStore.items.sumOf { it.quantity },
                    transitionSpec = {
                        slideInVertically { it } + fadeIn() togetherWith
                                slideOutVertically { -it } + fadeOut()
                    },
                    label = "total_items"
                ) { total ->
                    Text(
                        text = total.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total a pagar:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                AnimatedContent(
                    targetState = CartStore.getTotal(),
                    transitionSpec = {
                        slideInVertically { it } + fadeIn() togetherWith
                                slideOutVertically { -it } + fadeOut()
                    },
                    label = "total_price"
                ) { total ->
                    Text(
                        text = "$${total}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MarronBoton
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                colors = ButtonDefaults.buttonColors(MarronBoton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                pressed = true
                                tryAwaitRelease()
                                pressed = false
                            }
                        )
                    },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Proceder al Pago",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EmptyCartView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFFBDBDBD),
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Tu carrito está vacío",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9E9E9E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Agrega productos para comenzar",
                    fontSize = 14.sp,
                    color = Color(0xFFBDBDBD)
                )
            }
        }
    }
}