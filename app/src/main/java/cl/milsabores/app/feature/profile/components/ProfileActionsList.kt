package cl.milsabores.app.feature.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileActionsList(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        ActionItem(text = "Editar perfil", onClick = onEditProfile)

        ActionItem(text = "Mi historial", onClick = { /* TODO */ })

        ActionItem(text = "Cerrar sesión", onClick = onLogout, isDestructive = true)
    }
}

@Composable
private fun ActionItem(
    text: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val color = if (isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp)
    )
}
