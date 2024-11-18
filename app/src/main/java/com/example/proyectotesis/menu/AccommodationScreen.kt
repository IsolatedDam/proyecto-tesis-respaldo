package com.example.proyectotesis.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import coil.compose.rememberAsyncImagePainter
import com.example.proyectotesis.data.Accommodation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationScreen(
    accommodations: List<Accommodation>,
    statusMessage: String?, // Mensaje de estado opcional
    onAccommodationClick: (Accommodation) -> Unit,
    onCreateAccommodation: () -> Unit, // Acción para crear alojamiento
    onUpdateAccommodation: (Accommodation) -> Unit, // Acción para actualizar alojamiento
    onDeleteAccommodation: (Accommodation) -> Unit, // Acción para eliminar alojamiento
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Alojamientos Recomendados") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            },
            actions = {
                // Botón para agregar nuevo alojamiento
                IconButton(onClick = onCreateAccommodation) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Agregar Alojamiento")
                }
            }
        )

        // Mostrar mensaje de estado como un Snackbar
        statusMessage?.let { message ->
            Snackbar(
                action = {
                    TextButton(onClick = { /* Manejar acción del mensaje */ }) {
                        Text("OK")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = message)
            }
        }

        // Mostrar la lista de alojamientos
        LazyColumn {
            items(accommodations) { accommodation ->
                AccommodationItem(
                    accommodation = accommodation,
                    onClick = onAccommodationClick,
                    onUpdate = onUpdateAccommodation,
                    onDelete = onDeleteAccommodation
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AccommodationItem(
    accommodation: Accommodation,
    onClick: (Accommodation) -> Unit,
    onUpdate: (Accommodation) -> Unit,
    onDelete: (Accommodation) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(accommodation) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(accommodation.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(accommodation.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(accommodation.description, fontSize = 14.sp, color = Color.Gray)
                Text("Calificación: ${accommodation.rating}", fontSize = 12.sp, color = Color.Gray)
            }

            IconButton(onClick = { onUpdate(accommodation) }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
            }

            IconButton(onClick = { onDelete(accommodation) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar")
            }
        }
    }
}