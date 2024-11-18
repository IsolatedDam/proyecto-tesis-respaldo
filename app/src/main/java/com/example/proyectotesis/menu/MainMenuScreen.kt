package com.example.proyectotesis.ui.mainmenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.proyectotesis.R
import com.example.proyectotesis.viewmodel.PersonaViewModel

@Composable
fun MainMenuScreen(
    onProfileSettingsClick: () -> Unit, // Nuevo parámetro
    onImageDetectionClick: () -> Unit,
    onTouristRoutesClick: () -> Unit,
    onAccommodationClick: () -> Unit,
    onUserManagementClick: () -> Unit,
    personaViewModel: PersonaViewModel
) {
    val userRole by personaViewModel.userRole.collectAsState(initial = "user")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E5F5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.nayeon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Explora y descubre",
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Descubre lugares, rutas y alojamientos para tus próximas aventuras.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                label = "Configuración de Perfil", // Botón para perfil
                icon = Icons.Default.Person,
                onClick = onProfileSettingsClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                label = "Detección de Imágenes",
                icon = Icons.Default.CameraAlt,
                onClick = onImageDetectionClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                label = "Rutas Turísticas",
                icon = Icons.Default.Map,
                onClick = onTouristRoutesClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                label = "Lugares de Alojamiento",
                icon = Icons.Default.Hotel,
                onClick = onAccommodationClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (userRole == "admin") {
                MenuButton(
                    label = "Gestión de Usuarios",
                    icon = Icons.Default.Person,
                    onClick = onUserManagementClick
                )
            }
        }
    }
}

@Composable
fun MenuButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}