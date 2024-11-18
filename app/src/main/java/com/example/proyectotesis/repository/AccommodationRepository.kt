package com.example.proyectotesis.repository

import android.util.Log
import com.example.proyectotesis.data.Accommodation
import com.example.proyectotesis.network.AccommodationService
import retrofit2.Response

class AccommodationRepository(private val apiService: AccommodationService) {

    // Obtener todos los alojamientos (suspend function)
    suspend fun getAllAccommodations(): List<Accommodation>? {
        return try {
            val response: Response<List<Accommodation>> = apiService.getAllAccommodations()  // Llamada suspendida
            if (response.isSuccessful) {
                response.body()  // Obtiene el cuerpo de la respuesta
            } else {
                Log.e("AccommodationRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AccommodationRepository", "Error en getAllAccommodations: ${e.message}")
            null
        }
    }

    // Crear un alojamiento (suspend function)
    suspend fun createAccommodation(accommodation: Accommodation): Accommodation? {
        return try {
            val response: Response<Accommodation> = apiService.createAccommodation(accommodation)  // Llamada suspendida
            if (response.isSuccessful) {
                response.body()  // Obtiene el cuerpo de la respuesta
            } else {
                Log.e("AccommodationRepository", "Error al crear alojamiento: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AccommodationRepository", "Error al crear alojamiento: ${e.message}")
            null
        }
    }

    // Actualizar un alojamiento (suspend function)
    suspend fun updateAccommodation(id: Int, accommodation: Accommodation): Accommodation? {
        return try {
            val response: Response<Accommodation> = apiService.updateAccommodation(id, accommodation)  // Llamada suspendida
            if (response.isSuccessful) {
                response.body()  // Obtiene el cuerpo de la respuesta
            } else {
                Log.e("AccommodationRepository", "Error al actualizar alojamiento: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AccommodationRepository", "Error al actualizar alojamiento: ${e.message}")
            null
        }
    }

    // Eliminar un alojamiento (suspend function)
    suspend fun deleteAccommodation(id: Int): Boolean {
        return try {
            val response: Response<Void> = apiService.deleteAccommodation(id)  // Llamada suspendida
            response.isSuccessful  // Verifica si la respuesta fue exitosa
        } catch (e: Exception) {
            Log.e("AccommodationRepository", "Error al eliminar alojamiento: ${e.message}")
            false
        }
    }
}