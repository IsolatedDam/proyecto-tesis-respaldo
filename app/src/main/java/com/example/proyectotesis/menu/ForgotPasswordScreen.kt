package com.example.proyectotesis.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.repository.PersonaRepository
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    personaRepository: PersonaRepository = org.koin.androidx.compose.get(), // Inyección automática
    onBackToLoginClick: () -> Unit
) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recuperar Contraseña",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    if (email.value.text.isNotEmpty()) {
                        try {
                            val personas = personaRepository.getAllPersonas() // Obtener la lista de usuarios
                            val userExists = personas.any { it.email == email.value.text }

                            message = if (userExists) {
                                "Si el correo electrónico es válido, recibirás un enlace para restablecer tu contraseña."
                            } else {
                                "El correo electrónico no está registrado."
                            }
                        } catch (e: Exception) {
                            message = "Error al enviar la solicitud. Inténtalo más tarde."
                        }
                    } else {
                        message = "Por favor, ingresa un correo electrónico válido."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Solicitud")
        }

        Spacer(modifier = Modifier.height(8.dp))

        message?.let {
            Text(
                text = it,
                color = if (it.contains("Error")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { onBackToLoginClick() }) {
            Text(text = "Volver al inicio de sesión")
        }
    }
}