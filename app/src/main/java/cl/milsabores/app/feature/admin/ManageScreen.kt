package cl.milsabores.app.feature.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton

@Composable
fun ManageScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onAddProduct: () -> Unit,
    onAddCategory: () -> Unit,
    onAddAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gestión",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 4.dp)
            )

            Text(
                text = "Elige qué quieres administrar:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ManageButton(
                        text = "Agregar producto",
                        onClick = onAddProduct
                    )
                    ManageButton(
                        text = "Agregar categoría",
                        onClick = onAddCategory
                    )
                    ManageButton(
                        text = "Agregar cuenta de usuario",
                        onClick = onAddAccount
                    )
                }
            }
        }
    }
}

@Composable
private fun ManageButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MarronBoton
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text)
    }
}
