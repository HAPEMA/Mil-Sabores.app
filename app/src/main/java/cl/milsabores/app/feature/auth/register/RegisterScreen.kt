package cl.milsabores.app.feature.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import cl.milsabores.app.core.ui.theme.*

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onGoLogin: () -> Unit,
    onBack: () -> Unit
) {
    var nombres by remember { mutableStateOf("") }
    var apellidoP by remember { mutableStateOf("") }
    var apellidoM by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

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

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Crear Cuenta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextoPrincipal
                )

                Text(
                    text = "Completa tus datos para registrarte",
                    fontSize = 14.sp,
                    color = TextoPrincipal.copy(alpha = 0.7f)
                )

                Spacer(Modifier.height(22.dp))

                // NOMBRES
                OutlinedTextField(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = { Text("Nombres") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MarronBoton)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
                )

                Spacer(Modifier.height(16.dp))

                // APELLIDO PATERNO
                OutlinedTextField(
                    value = apellidoP,
                    onValueChange = { apellidoP = it },
                    label = { Text("Apellido Paterno") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                    ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
                )

                Spacer(Modifier.height(16.dp))

                // APELLIDO MATERNO
                OutlinedTextField(
                    value = apellidoM,
                    onValueChange = { apellidoM = it },
                    label = { Text("Apellido Materno") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                    ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
                )

                Spacer(Modifier.height(16.dp))

                // EMAIL
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                    ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
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
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = MarronBoton)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
                )

                Spacer(Modifier.height(16.dp))

                // FECHA NACIMIENTO
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text("Fecha de nacimiento") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MarronBoton
                        )
                    }
                    ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )
                )

                Spacer(Modifier.height(26.dp))

                Button(
                    onClick = onRegister,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarronBoton
                    )
                ) {
                    Text(
                        "Registrarme",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                }

                Spacer(Modifier.height(14.dp))

                TextButton(onClick = onBack) {
                    Text("Volver", color = MarronBoton)
                }

                TextButton(onClick = onGoLogin) {
                    Text("¿Ya tienes cuenta? Inicia sesión aquí", color = MarronBoton)
                }
            }
        }
    }
}
