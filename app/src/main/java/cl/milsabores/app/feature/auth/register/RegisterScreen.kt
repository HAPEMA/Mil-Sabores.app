package cl.milsabores.app.feature.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.milsabores.app.core.domain.model.AuthFakeStore
import cl.milsabores.app.core.ui.theme.Blanco
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import cl.milsabores.app.core.ui.theme.TextoPrincipal
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit, // puedes mandarlo a Home o a Login
    onGoLogin: () -> Unit,
    onBack: () -> Unit
) {
    var nombres by remember { mutableStateOf("") }
    var apellidoP by remember { mutableStateOf("") }
    var apellidoM by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = CremaFondo
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Brush.verticalGradient(listOf(CremaFondo, CremaFondo.copy(alpha = 0.85f)))),
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
                    Text("Crear Cuenta", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextoPrincipal)
                    Text("Completa tus datos para registrarte", fontSize = 14.sp, color = TextoPrincipal.copy(alpha = 0.7f))

                    Spacer(Modifier.height(22.dp))

                    @Composable
                    fun fieldColors() = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MarronBoton,
                        focusedLabelColor = MarronBoton,
                        cursorColor = MarronBoton
                    )

                    OutlinedTextField(
                        value = nombres, onValueChange = { nombres = it },
                        label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = apellidoP, onValueChange = { apellidoP = it },
                        label = { Text("Apellido Paterno") }, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = apellidoM, onValueChange = { apellidoM = it },
                        label = { Text("Apellido Materno") }, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email, onValueChange = { email = it },
                        label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password, onValueChange = { password = it },
                        label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = birthDate, onValueChange = { birthDate = it },
                        label = { Text("Fecha de nacimiento") }, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.DateRange, null, tint = MarronBoton) },
                        enabled = !loading, colors = fieldColors()
                    )

                    Spacer(Modifier.height(22.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                loading = true
                                try {
                                    val ok = AuthFakeStore.register(email, password)
                                    if (ok) {
                                        snackbarHostState.showSnackbar("Registro exitoso ✅")
                                        onGoLogin()
                                    } else {
                                        snackbarHostState.showSnackbar("No se pudo registrar (correo ya existe o campos vacíos) ❌")
                                    }
                                } finally {
                                    loading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(containerColor = MarronBoton),
                        enabled = !loading
                    ) {
                        Text("Registrarme", color = Blanco, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(12.dp))

                    TextButton(onClick = onBack, enabled = !loading) { Text("Volver", color = MarronBoton) }
                    TextButton(onClick = onGoLogin, enabled = !loading) { Text("¿Ya tienes cuenta? Inicia sesión aquí", color = MarronBoton) }
                }
            }
        }
    }
}
