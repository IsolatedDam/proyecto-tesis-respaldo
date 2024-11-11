package com.example.proyectotesis.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    scope: CoroutineScope,
    onBackToLoginClick: () -> Unit
) {
    // Estado para almacenar el correo electrónico ingresado
    val email = remember { mutableStateOf(TextFieldValue()) }
    var message by remember { mutableStateOf<String?>(null) }

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

        // Campo de texto para el correo electrónico
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para enviar solicitud de recuperación
        Button(
            onClick = {
                scope.launch {
                    // Aquí podrías añadir lógica para verificar y enviar un correo de recuperación
                    message = "Si el correo electrónico es válido, recibirás un enlace para restablecer tu contraseña."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Solicitud")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar mensaje de confirmación o error
        message?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para volver al inicio de sesión
        TextButton(onClick = { onBackToLoginClick() }) {
            Text(text = "Volver al inicio de sesión")
        }
    }
}