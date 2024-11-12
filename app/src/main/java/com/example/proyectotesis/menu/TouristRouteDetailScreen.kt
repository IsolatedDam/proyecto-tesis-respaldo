// TouristRouteDetailScreen.kt
package com.example.proyectotesis.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.proyectotesis.data.TouristRoute
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import com.example.proyectotesis.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TouristRouteDetailScreen(route: TouristRoute, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra superior con botón de retroceso
        TopAppBar(
            title = { Text(route.name) },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen de la ruta
        Image(
            painter = painterResource(id = R.drawable.nayeon), // Reemplaza "your_local_image" con el ID de tu imagen local
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        // Descripción y otros detalles
        Text(
            text = "Descripción",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = route.description,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Calificación
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Calificación: ${route.rating}",
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}