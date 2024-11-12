package com.example.proyectotesis.ui.imagedetectionscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Bitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background

@Composable
fun ImageDetectionScreen(
    onCaptureImageClick: () -> Unit,
    onSelectImageClick: () -> Unit,
    onDetectClick: () -> Unit,
    imageBitmap: Bitmap? = null,
    detectionResult: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Detección de Imágenes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Área de imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageBitmap != null) {
                Image(bitmap = imageBitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize())
            } else {
                Text("No hay imagen seleccionada", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones para capturar o seleccionar imagen
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCaptureImageClick) {
                Text("Abrir Cámara")
            }
            Button(onClick = onSelectImageClick) {
                Text("Seleccionar de Galería")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar la detección
        Button(
            onClick = onDetectClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Iniciar Detección")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Área de resultados de la detección
        Text(
            text = "Resultados:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(16.dp)
        ) {
            Text(
                text = detectionResult ?: "No hay resultados",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}