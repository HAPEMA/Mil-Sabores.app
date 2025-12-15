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
import cl.milsabores.app.core.domain.session.SessionManager

@Composable
fun MilSaboresTopBar(
    onGoHome: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val canManage = SessionManager.isAdmin
    // y en el dropdown/menu:
    if (canManage) { /* mostrar opción gestionar */ }

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
            }
        }
    }
}
