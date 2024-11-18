package com.example.proyectotesis.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.viewmodel.PersonaViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: PersonaViewModel,
    onLoginSuccess: () -> Unit, // Callback para redirigir tras el inicio de sesión exitoso
    onForgotPasswordClick: () -> Unit, // Callback para redirigir a la pantalla de recuperación de contraseña
    onRegisterClick: () -> Unit // Callback para redirigir a la pantalla de registro
) {
    val scope = rememberCoroutineScope()

    // Estados observables del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val authenticatedUser by viewModel.authenticatedUser.collectAsState()

    // Estados locales para los campos de entrada
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Efecto para redirigir al perfil si el usuario está autenticado
    LaunchedEffect(authenticatedUser) {
        if (authenticatedUser != null) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo de entrada para el nombre de usuario
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de entrada para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Botón de inicio de sesión
        Button(
            onClick = {
                if (nombre.isEmpty() || password.isEmpty()) {
                    viewModel.setStatusMessage("Por favor, completa todos los campos.")
                } else {
                    scope.launch {
                        val isAuthenticated = viewModel.authenticateUser(nombre, password)
                        if (!isAuthenticated) {
                            viewModel.setStatusMessage("Error: Nombre de usuario o contraseña incorrectos.")
                        }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = "Iniciar Sesión")
            }
        }

        // Opciones adicionales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onForgotPasswordClick) {
                Text(text = "¿Olvidaste tu contraseña?")
            }
            TextButton(onClick = onRegisterClick) {
                Text(text = "Crear cuenta nueva")
            }
        }

        // Mensaje de estado
        Spacer(modifier = Modifier.height(16.dp))
        statusMessage?.let {
            Text(
                text = it,
                color = if (it.contains("correctamente", ignoreCase = true)) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 14.sp
            )
        }
    }
}