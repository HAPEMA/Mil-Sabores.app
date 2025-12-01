package cl.milsabores.app.feature.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.R

@Composable
fun ProfileAvatarEditable(
    avatarUrl: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = avatarUrl ?: R.drawable.ic_profile_placeholder,
            contentDescription = "Foto de perfil",
            placeholder = painterResource(R.drawable.ic_profile_placeholder),
            error = painterResource(R.drawable.ic_profile_placeholder),
            modifier = Modifier.fillMaxSize()
        )
    }
}
