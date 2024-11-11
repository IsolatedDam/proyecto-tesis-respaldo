package com.example.proyectotesis.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.data.LocalUserRepository
import com.example.proyectotesis.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.background

@Composable
fun RegisterScreen(
    scope: CoroutineScope,
    onBackToLoginClick: () -> Unit
) {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Estado para el nivel de seguridad de la contraseña
    var passwordStrength by remember { mutableStateOf(PasswordStrength.WEAK) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la contraseña y validación de seguridad
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordStrength = calculatePasswordStrength(it.text)
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Barra de seguridad de la contraseña
        PasswordStrengthBar(strength = passwordStrength)

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para crear la cuenta
        Button(
            onClick = {
                scope.launch {
                    when {
                        username.value.text.isEmpty() -> {
                            errorMessage.value = "Por favor, ingresa un nombre de usuario."
                        }
                        email.value.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.text).matches() -> {
                            errorMessage.value = "Por favor, ingresa un correo electrónico válido."
                        }
                        password.value.text.isEmpty() -> {
                            errorMessage.value = "Por favor, ingresa una contraseña."
                        }
                        else -> {
                            val newUser = User(
                                username = username.value.text,
                                email = email.value.text,
                                password = password.value.text
                            )
                            LocalUserRepository.insertUser(newUser)
                            errorMessage.value = null
                            onBackToLoginClick()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(
            onClick = { onBackToLoginClick() }
        ) {
            Text(text = "¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}

// Enum para representar el nivel de seguridad de la contraseña
enum class PasswordStrength(val color: Color, val message: String) {
    WEAK(Color.Red, "No es segura"),
    MEDIUM(Color.Yellow, "Casi segura"),
    STRONG(Color.Green, "Segura")
}

// Función para calcular el nivel de seguridad de la contraseña
fun calculatePasswordStrength(password: String): PasswordStrength {
    return when {
        password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() } -> PasswordStrength.STRONG
        password.length >= 6 -> PasswordStrength.MEDIUM
        else -> PasswordStrength.WEAK
    }
}

// Composable para la barra de seguridad de la contraseña
@Composable
fun PasswordStrengthBar(strength: PasswordStrength) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(strength.color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = strength.message,
            color = strength.color,
            fontSize = 14.sp
        )
    }
}