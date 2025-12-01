package cl.milsabores.app.feature.contact

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.milsabores.app.core.ui.components.MilSaboresTopBar
import cl.milsabores.app.core.ui.theme.CremaFondo
import cl.milsabores.app.core.ui.theme.MarronBoton

@Composable
fun ContactScreen(
    onGoHome: () -> Unit,
    onGoManage: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit
) {
    val context = LocalContext.current

    // Campos del formulario (solo UI por ahora)
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CremaFondo)
    ) {

        MilSaboresTopBar(
            onGoHome = onGoHome,
            onGoManage = onGoManage,
            onGoCart = onGoCart,
            onGoProfile = onGoProfile
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Contáctanos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "¿Tienes dudas o necesitas más información? Escríbenos y te responderemos rápido.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            // Para mobile es más cómodo apilado vertical
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // --- TARJETA INFO + MAPA ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Información de contacto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Text("+56 9 1234 1234")
                        Text("contacto@milsabores.cl")

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Duoc UC, Sede Alameda\nSantiago, Chile",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(Modifier.height(12.dp))

                        // "Mapa": realmente es un botón que abre Maps
                        Button(
                            onClick = {
                                // Coordenadas aproximadas de Duoc Alameda, ajústalas si quieres
                                val gmmIntentUri = Uri.parse(
                                    "geo:-33.457391,-70.662671?q=Duoc+UC+Alameda"
                                )
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                    setPackage("com.google.android.apps.maps")
                                }

                                // Si hay app de Maps, la abre; si no, abre navegador
                                if (mapIntent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(mapIntent)
                                } else {
                                    val webUri = Uri.parse(
                                        "https://www.google.com/maps/place/Duoc+UC:+Sede+Alameda/@-33.4489424,-70.6725794,17z/data=!3m1!4b1!4m6!3m5!1s0x9662c45558896371:0xe38282e406413635!8m2!3d-33.4489469!4d-70.6700045!16s%2Fg%2F1tfjgr0k?entry=ttu&g_ep=EgoyMDI1MTEyMy4xIKXMDSoASAFQAw%3D%3D"
                                    )
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, webUri)
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MarronBoton),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver ubicación en el mapa")
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                // Abrir WhatsApp con mensaje predefinido
                                val url =
                                    "https://wa.me/56974042962?text=Hola%20quiero%20hacer%20una%20consulta"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(MarronBoton),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Háblanos por WhatsApp")
                        }
                    }
                }

                // --- TARJETA FORMULARIO (SOLO UI) ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Envíanos un mensaje",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Nombre *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = { Text("Email *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = subject.value,
                            onValueChange = { subject.value = it },
                            label = { Text("Asunto *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = message.value,
                            onValueChange = { message.value = it },
                            label = { Text("Descripción *") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 6
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                // Por ahora NO hace nada real, solo puedes limpiar o dejar TODO:
                                // name.value = ""
                                // email.value = ""
                                // subject.value = ""
                                // message.value = ""
                            },
                            colors = ButtonDefaults.buttonColors(MarronBoton),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text("Enviar")
                        }
                    }
                }
            }
        }
    }
}
