package com.example.proyectotesis.ui.profile

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectotesis.viewmodel.PersonaViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ProfileSettingsScreen(
    personaViewModel: PersonaViewModel,
    initialUsername: String, // Nombre de usuario inicial
    onProfileSaved: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Estados para almacenar los datos del perfil
    var username by remember { mutableStateOf(TextFieldValue(initialUsername)) }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var sex by remember { mutableStateOf<String?>(null) }
    var sexMenuExpanded by remember { mutableStateOf(false) }

    // Código de país seleccionado
    var selectedCountryCode by remember { mutableStateOf("+56") }
    var countryCodeMenuExpanded by remember { mutableStateOf(false) }

    // Lista de códigos de país
    val countryCodes = listOf(
        "Argentina (+54)" to "+54",
        "Chile (+56)" to "+56",
        "Colombia (+57)" to "+57",
        "España (+34)" to "+34",
        "Estados Unidos (+1)" to "+1",
        "México (+52)" to "+52"
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Configura tu Perfil",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto para el nombre de usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de teléfono con código de país y número de teléfono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector de código de país
                OutlinedButton(
                    onClick = { countryCodeMenuExpanded = true },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = selectedCountryCode)
                }
                DropdownMenu(
                    expanded = countryCodeMenuExpanded,
                    onDismissRequest = { countryCodeMenuExpanded = false }
                ) {
                    countryCodes.forEach { (countryName, code) ->
                        DropdownMenuItem(
                            text = { Text(countryName) },
                            onClick = {
                                selectedCountryCode = code
                                countryCodeMenuExpanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Campo de texto para el número de teléfono
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = if (it.text.length <= 10) it else TextFieldValue(it.text.take(10))
                    },
                    label = { Text("Número de Teléfono") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Fecha de Cumpleaños
            OutlinedButton(
                onClick = {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context, // Utiliza el contexto obtenido
                        { _, year, month, dayOfMonth ->
                            val selectedCalendar = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            selectedDate = selectedCalendar
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedDate != null) {
                        "${selectedDate!!.get(Calendar.DAY_OF_MONTH)}/${selectedDate!!.get(Calendar.MONTH) + 1}/${selectedDate!!.get(Calendar.YEAR)}"
                    } else {
                        "Seleccionar Fecha de Cumpleaños"
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Selección de Sexo con menú desplegable
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { sexMenuExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = sex ?: "Género")
                }
                DropdownMenu(
                    expanded = sexMenuExpanded,
                    onDismissRequest = { sexMenuExpanded = false }
                ) {
                    listOf("Masculino", "Femenino").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                sex = option
                                sexMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para guardar el perfil
            Button(
                onClick = {
                    val profileData = mapOf(
                        "telefono" to "$selectedCountryCode ${phoneNumber.text}",
                        "fechaNacimiento" to (selectedDate?.timeInMillis?.toString() ?: ""),
                        "genero" to (sex ?: ""),
                        "username" to username.text
                    )
                    scope.launch {
                        personaViewModel.saveUserProfile(profileData)
                        onProfileSaved(username.text)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Guardar", fontSize = 18.sp)
            }
        }
    }
}