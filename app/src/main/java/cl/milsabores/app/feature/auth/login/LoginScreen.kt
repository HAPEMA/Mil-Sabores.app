package cl.milsabores.app.feature.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import cl.milsabores.app.core.ui.theme.TextoPrincipal
import cl.milsabores.app.core.ui.theme.Blanco

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onGoRegister: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        CremaFondo,
                        CremaFondo.copy(alpha = 0.85f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .padding(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Blanco)
        ) {

            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // LOGO (sin painterResource)


                Spacer(Modifier.height(16.dp))

                Text(
                    text = "¡Bienvenido de nuevo!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoPrincipal
                )

                Text(
                    text = "Ingresa tus datos para continuar",
                    fontSize = 14.sp,
                    color = TextoPrincipal.copy(alpha = 0.7f)
                )

                Spacer(Modifier.height(22.dp))

                // EMAIL
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))

                // PASSWORD
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                )

                Spacer(Modifier.height(26.dp))

                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarronBoton
                    )
                ) {
                    Text(
                        "Iniciar Sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                }

                Spacer(Modifier.height(14.dp))

                TextButton(onClick = onBack) {
                    Text("Volver", color = MarronBoton)
                }

                TextButton(onClick = onGoRegister) {
                    Text("¿No tienes cuenta? Regístrate aquí", color = MarronBoton)
                }
            }
        }
    }
}
