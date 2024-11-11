package com.example.proyectotesis.ui.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeUserScreen(
    username: String,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    // Iniciar una corrutina que navegue automáticamente al MainMenuScreen después de unos segundos
    LaunchedEffect(Unit) {
        scope.launch {
            delay(3000) // Espera de 3 segundos
            navController.navigate("main_menu") // Navega al menú principal
        }
    }

    // Contenido de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido, $username!",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}