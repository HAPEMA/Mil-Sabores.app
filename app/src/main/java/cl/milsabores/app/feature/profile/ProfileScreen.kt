package cl.milsabores.app.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.milsabores.app.feature.profile.components.ProfileActionsList
import cl.milsabores.app.feature.profile.components.ProfileHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Mi Perfil") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            ProfileHeader(
                name = state.name,
                email = state.email,
                avatarUrl = state.avatarUrl
            )

            Spacer(Modifier.height(20.dp))

            ProfileActionsList(
                onEditProfile = onEditProfile,
                onLogout = onLogout
            )
        }
    }
}
