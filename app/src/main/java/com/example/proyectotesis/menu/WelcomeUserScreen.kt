package com.example.proyectotesis.ui.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectotesis.ui.theme.Routes
import kotlinx.coroutines.delay

@Composable
fun WelcomeUserScreen(
    username: String,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        delay(3000) // Espera 3 segundos
        navController.navigate(Routes.MAIN_MENU) {
            popUpTo(Routes.WELCOME_USER) { inclusive = true } // Elimina esta pantalla de la pila
        }
    }

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
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}