package cl.milsabores.app.feature.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.R

@Composable
fun ProfileHeader(
    name: String,
    email: String,
    avatarUrl: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = avatarUrl ?: R.drawable.ic_profile_placeholder,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(16.dp))

        Text(name, style = MaterialTheme.typography.titleLarge)
        Text(email, style = MaterialTheme.typography.bodyMedium)
    }
}
