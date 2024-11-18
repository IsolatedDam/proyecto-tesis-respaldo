package com.example.proyectotesis.ui

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
import com.example.proyectotesis.data.TouristRoute
import androidx.compose.material.icons.Icons
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TouristRoutesScreen(
    routes: List<TouristRoute>,
    onRouteClick: (TouristRoute) -> Unit,
    onBack: () -> Unit // Agregamos un parámetro para manejar la navegación hacia atrás
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra superior con botón de retroceso
        TopAppBar(
            title = { Text("Rutas Turísticas en Santiago") },
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

        LazyColumn {
            items(routes) { route ->
                TouristRouteItem(route, onRouteClick)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TouristRouteItem(
    route: TouristRoute,
    onClick: (TouristRoute) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick(route) }
        ) {
            // Cargar imagen desde la URL de la ruta
            Image(
                painter = rememberAsyncImagePainter(model = route.imageUrl), // Usamos la URL de la ruta
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Detalles de la ruta
            Column {
                Text(route.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(route.description, fontSize = 14.sp, color = Color.Gray)
                Text("Calificación: ${route.rating}", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}