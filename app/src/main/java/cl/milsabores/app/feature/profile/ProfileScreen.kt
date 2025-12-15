package cl.milsabores.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.data.local.DatabaseProvider
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.feature.profile.components.CompraCard
import cl.milsabores.app.feature.profile.components.ProfileImageEditor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val user = SessionManager.currentUser
    var compras by remember { mutableStateOf<List<CompraEntity>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }

    // Si no hay sesión, afuera
    LaunchedEffect(user) {
        if (user == null) {
            onLogout()
            return@LaunchedEffect
        }

        loading = true
        try {
            val db = DatabaseProvider.get(context)
            compras = db.compraDao().getComprasByUsuario(user.id)
        } finally {
            loading = false
        }
    }

    if (user == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
            .padding(16.dp)
    ) {

        @Composable
        fun ProfileHeader(
            user: cl.milsabores.app.core.data.local.user.UserEntity,
            photoContent: @Composable () -> Unit
        ) {
            Column {
                photoContent()
                Spacer(Modifier.height(12.dp))
                Text("${user.nombres} ${user.apellidoP} ${user.apellidoM}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(user.email, style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(20.dp))

        // ✅ LOGOUT
        Button(
            onClick = {
                SessionManager.logout()
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }

        Spacer(Modifier.height(24.dp))

        Text("Historial de compras", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        if (loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else if (compras.isEmpty()) {
            Text("Aún no tienes compras")
        } else {
            compras.forEach { compra ->
                CompraCard(compra)
            }
        }
    }
}
