package com.example.proyectotesis.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.proyectotesis.network.AppUser
import com.example.proyectotesis.viewmodel.PersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserManagementScreen(
    personaViewModel: PersonaViewModel,
    onBackClick: () -> Unit
) {
    // Observar los datos de personas y el estado seleccionado
    val personas by personaViewModel.personas.collectAsState()
    val selectedPersona by personaViewModel.selectedPersona.collectAsState()
    val statusMessage by personaViewModel.statusMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gestión de Usuarios") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Lista de Usuarios",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Lista de usuarios
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(personas) { persona ->
                        PersonaItem(
                            persona = persona,
                            onClick = {
                                // Llamada para cargar la persona seleccionada
                                personaViewModel.fetchPersonaById(persona.idUsuario)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Detalles del usuario seleccionado
                selectedPersona?.let { persona ->
                    UserEditSection(
                        persona = persona,
                        onSave = { updatedPersona ->
                            personaViewModel.updatePersona(
                                updatedPersona.idUsuario,
                                mapOf(
                                    "nombre" to updatedPersona.nombre,
                                    "email" to updatedPersona.email,
                                    "rol" to updatedPersona.rol
                                )
                            )
                        },
                        onDelete = {
                            personaViewModel.deletePersona(persona.idUsuario)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de estado
                statusMessage?.let {
                    Text(
                        text = it,
                        color = if (it.contains("éxito", ignoreCase = true)) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun UserEditSection(
    persona: AppUser,
    onSave: (AppUser) -> Unit,
    onDelete: () -> Unit
) {
    Text(text = "Editar Usuario: ${persona.nombre}", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))

    var name by remember { mutableStateOf(persona.nombre) }
    var email by remember { mutableStateOf(persona.email) }
    var role by remember { mutableStateOf(persona.rol) }

    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text(text = "Nombre") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Selector de rol
    DropdownRoleSelector(selectedRole = role, onRoleChange = { role = it })

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        Button(
            onClick = {
                onSave(persona.copy(nombre = name, email = email, rol = role))
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Guardar Cambios")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Eliminar Usuario")
        }
    }
}

@Composable
fun DropdownRoleSelector(selectedRole: String, onRoleChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = { expanded = true }) {
            Text(text = "Rol: $selectedRole")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onRoleChange("user")
                    expanded = false
                },
                text = { Text(text = "user") }
            )
            DropdownMenuItem(
                onClick = {
                    onRoleChange("admin")
                    expanded = false
                },
                text = { Text(text = "admin") }
            )
        }
    }
}

@Composable
fun PersonaItem(
    persona: AppUser,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = persona.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = persona.email,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Rol: ${persona.rol}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}