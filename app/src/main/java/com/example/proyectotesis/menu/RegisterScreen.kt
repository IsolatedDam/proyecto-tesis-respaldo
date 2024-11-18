package com.example.proyectotesis.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.proyectotesis.viewmodel.PersonaViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    personaViewModel: PersonaViewModel,
    onRegisterSuccess: () -> Unit, // Callback para redirigir al LoginScreen
    onBackToLoginClick: () -> Unit // Callback para volver al LoginScreen
) {
    val scope = rememberCoroutineScope()

    // Estados locales para los campos de entrada
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Estados observables para mostrar mensajes de estado y control de carga
    val isLoading by personaViewModel.isLoading.collectAsState()
    val statusMessage by personaViewModel.statusMessage.collectAsState()

    // Contenido de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Crea una Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de entrada para el nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para el email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirma tu Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para registrarse
        Button(
            onClick = {
                if (password == confirmPassword) {
                    scope.launch {
                        val personaData = mapOf(
                            "nombre" to nombre,
                            "email" to email,
                            "contrasena" to password,
                            "rol" to "user"
                        )

                        val response = personaViewModel.createPersona(personaData)

                        if (response?.status == "success") {
                            personaViewModel.setStatusMessage("Usuario creado exitosamente")
                            onRegisterSuccess() // Navegar a LoginScreen
                        } else {
                            personaViewModel.setStatusMessage("Error al crear el usuario: ${response?.message}")
                        }
                    }
                } else {
                    personaViewModel.setStatusMessage("Las contraseñas no coinciden")
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = "Registrar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error o estado
        statusMessage?.let {
            Text(
                text = it,
                color = if (it.contains("éxito", ignoreCase = true)) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver al inicio de sesión
        TextButton(onClick = onBackToLoginClick) {
            Text(text = "¿Ya tienes una cuenta? Inicia Sesión", color = MaterialTheme.colorScheme.primary)
        }
    }
}