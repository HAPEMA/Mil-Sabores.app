package cl.milsabores.app.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cl.milsabores.app.R

@Composable
fun MilSaboresTopBar(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_cupcake),
            contentDescription = "Pastelería Mil Sabores",
            modifier = Modifier.height(40.dp)
        )

        Spacer(Modifier.weight(1f))

        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Inicio") },
                    onClick = {
                        expanded = false
                        onGoHome()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Gestionar") },
                    onClick = {
                        expanded = false
                        onGoManage()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Carrito") },
                    onClick = {
                        expanded = false
                        onGoCart()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Perfil") },
                    onClick = {
                        expanded = false
                        onGoProfile()
                    }
                )
            }
        }
    }
}
