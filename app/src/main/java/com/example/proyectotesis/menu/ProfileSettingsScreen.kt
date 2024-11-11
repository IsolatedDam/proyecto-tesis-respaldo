package com.example.proyectotesis.ui.profile

import android.app.DatePickerDialog
import android.content.Context
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
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "profile")
val USERNAME_KEY = stringPreferencesKey("username")
val BIRTHDAY_KEY = stringPreferencesKey("birthday")
val PHONE_KEY = stringPreferencesKey("phone")
val SEX_KEY = stringPreferencesKey("sex")

@Composable
fun ProfileSettingsScreen(
    onProfileSaved: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Estados para almacenar los datos del perfil
    var username by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var sex by remember { mutableStateOf<String?>(null) }
    var sexMenuExpanded by remember { mutableStateOf(false) }

    // Código de país seleccionado
    var selectedCountryCode by remember { mutableStateOf("+56") } // Código predeterminado (Chile)
    var countryCodeMenuExpanded by remember { mutableStateOf(false) }

    // Lista de códigos de país
    val countryCodes = listOf(
        "Argentina (+54)" to "+54",
        "Australia (+61)" to "+61",
        "Brasil (+55)" to "+55",
        "Canadá (+1)" to "+1",
        "Chile (+56)" to "+56",
        "China (+86)" to "+86",
        "Colombia (+57)" to "+57",
        "España (+34)" to "+34",
        "Estados Unidos (+1)" to "+1",
        "Francia (+33)" to "+33",
        "Alemania (+49)" to "+49",
        "India (+91)" to "+91",
        "Italia (+39)" to "+39",
        "Japón (+81)" to "+81",
        "México (+52)" to "+52",
        "Países Bajos (+31)" to "+31",
        "Perú (+51)" to "+51",
        "Reino Unido (+44)" to "+44",
        "Rusia (+7)" to "+7",
        "Sudáfrica (+27)" to "+27"
    )

    // Cargar datos desde DataStore
    LaunchedEffect(Unit) {
        val preferences = context.dataStore.data.first()
        username = TextFieldValue(preferences[USERNAME_KEY] ?: "")
        preferences[BIRTHDAY_KEY]?.let {
            selectedDate = Calendar.getInstance().apply {
                timeInMillis = it.toLong()
            }
        }
        phoneNumber = TextFieldValue(preferences[PHONE_KEY] ?: "")
        sex = preferences[SEX_KEY]
    }

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

            // Campo de teléfono con código de país y número de teléfono en una fila
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector de código de país
                OutlinedButton(
                    onClick = { countryCodeMenuExpanded = true },
                    modifier = Modifier.width(100.dp) // Ancho del botón de código de país
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

                // Campo de texto para el número de teléfono con límite de 10 dígitos
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        // Limitar el texto a 10 caracteres, permitiendo borrar/modificar
                        phoneNumber = if (it.text.length <= 10) {
                            it
                        } else {
                            TextFieldValue(it.text.take(10))
                        }
                    },
                    label = { Text("Número de Teléfono") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Solo números
                    modifier = Modifier.weight(1f) // El campo de número de teléfono llena el espacio restante
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Fecha de Cumpleaños
            OutlinedButton(
                onClick = {
                    val today = Calendar.getInstance()
                    DatePickerDialog(
                        context,
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

            // Selección de Sexo con menú desplegable en la posición correcta
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

            // Botón para guardar la configuración de perfil
            Button(
                onClick = {
                    scope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[USERNAME_KEY] = username.text
                            preferences[BIRTHDAY_KEY] = selectedDate?.timeInMillis.toString()
                            preferences[PHONE_KEY] = "$selectedCountryCode ${phoneNumber.text}"
                            preferences[SEX_KEY] = sex ?: ""
                        }
                        // Redirigir a la pantalla de bienvenida personalizada
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