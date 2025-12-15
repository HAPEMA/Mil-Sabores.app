package cl.milsabores.app.feature.profile.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.data.local.DatabaseProvider
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity
import java.util.Date

@Composable
fun CompraCard(compra: CompraEntity) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("Fecha: ${Date(compra.fechaCreacion)}")
            Text("Total: ${compra.total} CLP")

            AnimatedVisibility(expanded) {
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
