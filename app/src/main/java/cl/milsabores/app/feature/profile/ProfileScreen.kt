package cl.milsabores.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Mi perfil",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Aquí luego mostraremos los datos del usuario.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
