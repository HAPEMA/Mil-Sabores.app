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
import cl.milsabores.app.core.data.remote.SupabaseClientProvider
import cl.milsabores.app.core.ui.theme.Blanco
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton
import cl.milsabores.app.core.ui.theme.TextoPrincipal
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoRegister: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = CremaFondo
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(listOf(CremaFondo, CremaFondo.copy(alpha = 0.85f)))
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

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = MarronBoton) },
                        enabled = !loading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MarronBoton,
                            focusedLabelColor = MarronBoton,
                            cursorColor = MarronBoton
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = MarronBoton) },
                        enabled = !loading,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MarronBoton,
                            focusedLabelColor = MarronBoton,
                            cursorColor = MarronBoton
                        )
                    )

                    Spacer(Modifier.height(26.dp))

                    Button(
                        onClick = {
                            val em = email.trim()
                            val pw = password

                            if (em.isBlank() || pw.isBlank()) {
                                scope.launch { snackbarHostState.showSnackbar("Completa correo y contraseña.") }
                                return@Button
                            }

                            scope.launch {
                                loading = true
                                try {
                                    SupabaseClientProvider.client.auth.signInWith(Email) {
                                        this.email = em
                                        this.password = pw
                                    }
                                    snackbarHostState.showSnackbar("Login exitoso ✅")
                                    onLoginSuccess()
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Error al iniciar sesión: ${e.message}")
                                } finally {
                                    loading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MarronBoton),
                        enabled = !loading
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp),
                                color = Blanco
                            )
                            Spacer(Modifier.width(10.dp))
                            Text("Ingresando...", color = Blanco, fontWeight = FontWeight.Bold)
                        } else {
                            Text("Iniciar Sesión", color = Blanco, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    TextButton(onClick = onBack, enabled = !loading) {
                        Text("Volver", color = MarronBoton)
                    }
                    TextButton(onClick = onGoRegister, enabled = !loading) {
                        Text("¿No tienes cuenta? Regístrate aquí", color = MarronBoton)
                    }
                }
            }
        }
    }
}
