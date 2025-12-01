package cl.milsabores.app.feature.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileInfoField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = {},
        readOnly = true,
        modifier = modifier.fillMaxWidth()
    )
}
