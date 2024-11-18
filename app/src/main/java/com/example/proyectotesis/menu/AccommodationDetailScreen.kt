package com.example.proyectotesis.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.proyectotesis.data.Accommodation
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import com.example.proyectotesis.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationDetailScreen(
    accommodation: Accommodation?,
    onBack: () -> Unit
) {
    if (accommodation == null) {
        // Mostrar UI alternativa si los datos son nulos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Datos del alojamiento no disponibles",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text(accommodation.name) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(
                model = accommodation.imageUrl,
                placeholder = painterResource(id = R.drawable.nayeon), // Asegúrate de tener este recurso
                error = painterResource(id = R.drawable.nayeon)
            ),
            contentDescription = "Imagen de ${accommodation.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Descripción",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = accommodation.description,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Calificación: ${accommodation.rating}",
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}