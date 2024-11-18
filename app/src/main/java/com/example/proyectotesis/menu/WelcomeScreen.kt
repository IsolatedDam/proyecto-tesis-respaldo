package com.example.proyectotesis.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.R

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, onStartClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.nayeon), // Imagen local
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Fondo semitransparente
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)) // Fondo semitransparente negro
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Explora monumentos históricos de manera inteligente",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp) // Añadido un margen inferior para mejorar la estética
            )

            // Botón para empezar
            Button(
                onClick = { onStartClick() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(text = "Comenzar", color = Color.White)
            }
        }
    }
}