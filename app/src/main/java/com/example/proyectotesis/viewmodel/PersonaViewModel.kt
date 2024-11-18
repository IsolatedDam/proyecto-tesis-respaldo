package com.example.proyectotesis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotesis.network.AppUser
import com.example.proyectotesis.network.ResponseStatus
import com.example.proyectotesis.repository.PersonaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PersonaViewModel(private val repository: PersonaRepository) : ViewModel() {

    // Lista de usuarios
    private val _personas = MutableStateFlow<List<AppUser>>(emptyList())
    val personas: StateFlow<List<AppUser>> get() = _personas

    // Usuario seleccionado
    private val _selectedPersona = MutableStateFlow<AppUser?>(null)
    val selectedPersona: StateFlow<AppUser?> get() = _selectedPersona

    // Usuario autenticado
    private val _authenticatedUser = MutableStateFlow<AppUser?>(null)
    val authenticatedUser: StateFlow<AppUser?> get() = _authenticatedUser

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Mensaje de estado
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> get() = _statusMessage

    private val _userRole = MutableStateFlow("user") // Rol predeterminado
    val userRole: StateFlow<String> get() = _userRole


    // Obtener todas las personas
    fun getAllPersonas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedPersonas = repository.getAllPersonas()
                _personas.value = fetchedPersonas
                _statusMessage.value = "Usuarios cargados correctamente"
            } catch (e: Exception) {
                _statusMessage.value = "Error al cargar usuarios: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setStatusMessage(message: String) {
        _statusMessage.value = message
    }

    // Obtener una persona por ID
    // Obtener una persona por ID
    fun fetchPersonaById(personaId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val persona = repository.getPersonaById(personaId) // Retorna un AppUser directamente
                if (persona != null) {
                    _selectedPersona.value = persona
                    _statusMessage.value = "Usuario cargado correctamente"
                } else {
                    _statusMessage.value = "Usuario no encontrado"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al cargar usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Crear una nueva persona
    suspend fun createPersona(personaData: Map<String, String>): ResponseStatus<String>? {
        _isLoading.value = true
        return try {
            val response = repository.createPersona(personaData)
            if (response?.status == "success") {
                _statusMessage.value = "Usuario creado exitosamente"
                getAllPersonas() // Actualiza la lista de usuarios
            } else {
                _statusMessage.value = response?.message ?: "Error al crear usuario"
            }
            response
        } catch (e: Exception) {
            _statusMessage.value = "Error al crear usuario: ${e.message}"
            null
        } finally {
            _isLoading.value = false
        }
    }


    // Actualizar una persona
    fun updatePersona(personaId: Int, personaData: Map<String, Any>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updatePersona(personaId, personaData)
                if (response?.status == "success") {
                    _statusMessage.value = "Usuario actualizado exitosamente"
                    getAllPersonas()
                } else {
                    _statusMessage.value = response?.message ?: "Error al actualizar usuario"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al actualizar usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Eliminar una persona
    fun deletePersona(personaId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.deletePersona(personaId)
                if (response?.status == "success") {
                    _statusMessage.value = "Usuario eliminado exitosamente"
                    getAllPersonas()
                } else {
                    _statusMessage.value = response?.message ?: "Error al eliminar usuario"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al eliminar usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Autenticar usuario
    suspend fun authenticateUser(nombre: String, password: String): Boolean {
        return try {
            val response = repository.authenticateUser(nombre, password)
            if (response != null) {
                _authenticatedUser.value = response
                setStatusMessage("Usuario autenticado correctamente.")
                true
            } else {
                setStatusMessage("Nombre de usuario o contraseña incorrectos.")
                false
            }
        } catch (e: Exception) {
            setStatusMessage("Error al conectar con el servidor: ${e.message}")
            false
        }
    }

    fun saveUserProfile(profileData: Map<String, String>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.saveUserProfile(profileData)
                if (response?.status == "success") {
                    _statusMessage.value = "Perfil guardado correctamente"
                } else {
                    _statusMessage.value = response?.message ?: "Error al guardar perfil"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}