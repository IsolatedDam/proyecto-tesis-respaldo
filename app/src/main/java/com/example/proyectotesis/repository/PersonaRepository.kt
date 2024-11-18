package com.example.proyectotesis.repository

import android.util.Log
import com.example.proyectotesis.network.AppUser
import com.example.proyectotesis.network.PersonaService
import com.example.proyectotesis.network.ResponseStatus

class PersonaRepository(
    private val apiService: PersonaService
) {
    // Obtener todas las personas desde el backend
    suspend fun getAllPersonas(): List<AppUser> {
        return try {
            val response = apiService.getAllPersonas()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "success") {
                    body.data ?: emptyList()
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Crear una nueva persona en el backend
    suspend fun createPersona(personaData: Map<String, String>): ResponseStatus<String>? {
        return try {
            val response = apiService.createPersona(personaData)
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("PersonaRepository", "Error al crear persona. Código: ${response.code()}, Mensaje: $errorBody")
                ResponseStatus("error", null, errorBody ?: "Error desconocido")
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error al crear persona: ${e.message}")
            ResponseStatus("error", null, "Excepción: ${e.message}")
        }
    }

    // Obtener una persona por ID desde el backend
    // Obtener una persona por ID desde el backend
    suspend fun getPersonaById(personaId: Int): AppUser? {
        return try {
            val response = apiService.getPersonaById(personaId)
            if (response.isSuccessful) {
                response.body()?.data // Asumiendo que el AppUser está dentro de "data" en el ResponseStatus
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(
                    "PersonaRepository",
                    "Error al obtener persona por ID. Código: ${response.code()}, Mensaje: $errorBody"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error al obtener persona por ID: ${e.message}")
            null
        }
    }

    // Actualizar una persona en el backend
    suspend fun updatePersona(personaId: Int, personaData: Map<String, Any>): ResponseStatus<String>? {
        return try {
            val response = apiService.updatePersona(personaId, personaData)
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(
                    "PersonaRepository",
                    "Error al actualizar persona. Código: ${response.code()}, Mensaje: $errorBody"
                )
                ResponseStatus("error", null, errorBody ?: "Error desconocido")
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error al actualizar persona: ${e.message}")
            ResponseStatus("error", null, "Excepción: ${e.message}")
        }
    }

    // Eliminar una persona en el backend
    suspend fun deletePersona(personaId: Int): ResponseStatus<String>? {
        return try {
            val response = apiService.deletePersona(personaId)
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(
                    "PersonaRepository",
                    "Error al eliminar persona. Código: ${response.code()}, Mensaje: $errorBody"
                )
                ResponseStatus("error", null, errorBody ?: "Error desconocido")
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error al eliminar persona: ${e.message}")
            ResponseStatus("error", null, "Excepción: ${e.message}")
        }
    }

    // Autenticación de usuario con el backend
    suspend fun authenticateUser(nombre: String, password: String): AppUser? {
        return try {
            val response = apiService.login(mapOf("nombre" to nombre, "contrasena" to password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status == "success") {
                    // Crea el AppUser con los datos devueltos
                    body.user
                } else {
                    Log.e("PersonaRepository", "Error de autenticación: ${body?.message}")
                    null
                }
            } else {
                Log.e("PersonaRepository", "Error HTTP: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error en la autenticación: ${e.message}")
            null
        }
    }
    // Guardar o actualizar el perfil del usuario en el backend
    suspend fun saveUserProfile(profileData: Map<String, String>): ResponseStatus<String>? {
        return try {
            val response = apiService.saveUserProfile(profileData)
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(
                    "PersonaRepository",
                    "Error al guardar perfil. Código: ${response.code()}, Mensaje: $errorBody"
                )
                ResponseStatus("error", null, errorBody ?: "Error desconocido")
            }
        } catch (e: Exception) {
            Log.e("PersonaRepository", "Error al guardar perfil: ${e.message}")
            ResponseStatus("error", null, "Excepción: ${e.message}")
        }
    }

}