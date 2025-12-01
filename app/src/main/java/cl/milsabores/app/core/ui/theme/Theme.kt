package cl.milsabores.app.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography


// Usa la tipografía por defecto de Material3
val Typography = Typography()

private val LightColors = lightColorScheme(
    primary = MarronBoton,
    onPrimary = Color.White,
    background = CremaFondo,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal
)

@Composable
fun MilSaboresTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
