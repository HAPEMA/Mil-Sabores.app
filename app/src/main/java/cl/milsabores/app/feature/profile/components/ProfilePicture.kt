package cl.milsabores.app.feature.profile.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cl.milsabores.app.R

@Composable
fun ProfilePicture(photoUri: String?) {
    AsyncImage(
        model = photoUri,
        contentDescription = "Foto de perfil",
        placeholder = painterResource(R.drawable.ic_profile_placeholder),
        error = painterResource(R.drawable.ic_profile_placeholder),
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}
