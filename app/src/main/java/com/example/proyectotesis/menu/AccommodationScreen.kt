package com.example.proyectotesis.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import coil.compose.rememberAsyncImagePainter
import com.example.proyectotesis.data.Accommodation
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationScreen(
    accommodations: List<Accommodation>,
    onAccommodationClick: (Accommodation) -> Unit,
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
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        )

        LazyColumn {
            items(accommodations) { accommodation ->
                AccommodationItem(accommodation, onAccommodationClick)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AccommodationItem(
    accommodation: Accommodation,
    onClick: (Accommodation) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(accommodation) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Image(
//     painter = rememberAsyncImagePainter(accommodation.imageUrl),
//     contentDescription = null,
//     modifier = Modifier
//         .size(80.dp)
//         .clip(RoundedCornerShape(8.dp))
// )


            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(accommodation.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(accommodation.description, fontSize = 14.sp, color = Color.Gray)
                Text("Calificación: ${accommodation.rating}", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}