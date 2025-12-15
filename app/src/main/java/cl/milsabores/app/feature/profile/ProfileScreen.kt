package cl.milsabores.app.feature.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.core.data.local.DatabaseProvider
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity
import cl.milsabores.app.core.domain.session.SessionManager
import cl.milsabores.app.core.ui.theme.CremaFondo
import kotlinx.coroutines.launch
import java.util.Date
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val user = SessionManager.currentUser

    var compras by remember { mutableStateOf<List<CompraEntity>>(emptyList()) }

    LaunchedEffect(user?.id) {
        user ?: return@LaunchedEffect
        val db = DatabaseProvider.get(context)
        compras = db.compraDao().getComprasByUsuario(user.id)
    }

    if (user == null) {
        // Por seguridad: si no hay sesión, reenvía al login
        LaunchedEffect(Unit) { onLogout() }
        return
    }
    var showPickerMenu by remember { mutableStateOf(false) }
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult
        val uriStr = uri.toString()
        scope.launch {
            val db = DatabaseProvider.get(context)
            db.userDao().updatePhoto(user.id, uriStr)
            SessionManager.currentUser = user.copy(photoUri = uriStr)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        if (!ok) return@rememberLauncherForActivityResult
        val uri = cameraUri ?: return@rememberLauncherForActivityResult
        val uriStr = uri.toString()
        scope.launch {
            val db = DatabaseProvider.get(context)
            db.userDao().updatePhoto(user.id, uriStr)
            SessionManager.currentUser = user.copy(photoUri = uriStr)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { showPickerMenu = true },
            contentAlignment = Alignment.Center
        ) {
            if (user.photoUri.isNullOrBlank()) Text("Agregar foto")
            else AsyncImage(model = user.photoUri, contentDescription = null, modifier = Modifier.fillMaxSize())
        }

        DropdownMenu(
            expanded = showPickerMenu,
            onDismissRequest = { showPickerMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("Tomar foto") },
                onClick = {
                    showPickerMenu = false
                    val uri = createImageUri(context)
                    cameraUri = uri
                    cameraLauncher.launch(uri)
                }
            )
            DropdownMenuItem(
                text = { Text("Elegir de galería") },
                onClick = {
                    showPickerMenu = false
                    galleryLauncher.launch("image/*")
                }
            )
        }


        Spacer(Modifier.height(16.dp))

        // Logout pequeño (más chico que Button normal)
        TextButton(
            onClick = {
                SessionManager.logout()
                onLogout()
            },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Cerrar sesión")
        }

        Spacer(Modifier.height(12.dp))

        Text("Historial de compras", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        if (compras.isEmpty()) {
            Text("Aún no tienes compras")
        } else {
            compras.forEach { compra ->
                CompraCard(compra = compra)
            }
        }
    }
}
private fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}
@Composable
fun CompraCard(compra: CompraEntity) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var detalles by remember { mutableStateOf<List<DetalleCompraEntity>>(emptyList()) }

    LaunchedEffect(expanded) {
        if (expanded) {
            val db = DatabaseProvider.get(context)
            detalles = db.compraDao().getDetallesByCompra(compra.id)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("Fecha: ${Date(compra.fechaCreacion)}")
            Text("Total: ${compra.total} CLP")

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(8.dp))
                    detalles.forEach {
                        Text("• ${it.nombre} x${it.cantidad}")
                    }
                }
            }
        }
    }
}
