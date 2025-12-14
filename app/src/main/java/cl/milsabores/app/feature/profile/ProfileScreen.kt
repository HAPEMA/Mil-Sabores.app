package cl.milsabores.app.feature.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo

@Composable
fun ProfileScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit
) {
    var screenVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        screenVisible = true
    }

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

        AnimatedVisibility(
            visible = screenVisible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProfileImage()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Dwaith Santiago Lopez",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "dw.lopez@duocuc.cl",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Historial de compras:",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OrderCard()
                Spacer(modifier = Modifier.height(12.dp))
                OrderCard()
            }
        }
    }
}

@Composable
private fun ProfileImage() {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        label = "profileImageScale"
    )

    Box(
        modifier = Modifier
            .size(110.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable { pressed = !pressed },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Foto\nperfil",
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
    }
}

@Composable
private fun OrderCard() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "25/06/2025",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = if (expanded) "Ocultar" else "Ver detalles",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Productos: 2",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Total: 25.500 CLP",
                style = MaterialTheme.typography.bodySmall
            )

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Orden: #47",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
