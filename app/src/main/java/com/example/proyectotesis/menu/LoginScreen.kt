package com.example.proyectotesis.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.data.LocalUserRepository  // Asegúrate de importar LocalUserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

@Composable
fun LoginScreen(
    scope: CoroutineScope,
    onLoginSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit // Nuevo parámetro para navegar a la pantalla de recuperación
) {
    // Variables para almacenar el texto de usuario y contraseña
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    // Estado para almacenar el mensaje de error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el nombre de usuario
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la contraseña
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar sesión
        Button(
            onClick = {
                scope.launch {
                    if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                        errorMessage = "Por favor, completa ambos campos."
                    } else {
                        val user = LocalUserRepository.getUser(
                            username = username.value.text,
                            password = password.value.text
                        )
                        if (user != null) {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Usuario o contraseña incorrectos."
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Opción de "¿Olvidaste tu contraseña?"
        TextButton(onClick = { onForgotPasswordClick() }) {
            Text(text = "¿Olvidaste tu contraseña?")
        }

        // Opción para crear una cuenta nueva
        TextButton(onClick = { onBackToLoginClick() }) {
            Text(text = "Crear una cuenta nueva")
        }
    }
}