package cl.milsabores.app.feature.profile_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import cl.milsabores.app.feature.profile.ProfileViewModel
import cl.milsabores.app.feature.profile.components.ProfileAvatarEditable

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onGoBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf(state.name) }
    var email by remember { mutableStateOf(state.email) }
    var phone by remember { mutableStateOf(state.phone) }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { viewModel.updateAvatar(it.toString()) }
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {

        ProfileAvatarEditable(
            avatarUrl = state.avatarUrl,
            onClick = { picker.launch("image/*") }
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.updateProfile(name, email, phone)
                onGoBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}
