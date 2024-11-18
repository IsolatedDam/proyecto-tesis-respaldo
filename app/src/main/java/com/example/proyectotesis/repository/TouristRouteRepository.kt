package com.example.proyectotesis.repository

import android.util.Log
import com.example.proyectotesis.data.TouristRoute
import com.example.proyectotesis.network.TouristRouteService
import retrofit2.Response
import retrofit2.HttpException

class TouristRouteRepository(private val apiService: TouristRouteService) {

    // Obtener todas las rutas turísticas
    suspend fun getAllRoutes(): List<TouristRoute>? {
        return try {
            val response: Response<List<TouristRoute>> = apiService.getAllRoutes()  // Llamada suspendida
            if (response.isSuccessful) {
                response.body()  // Obtener la respuesta
            } else {
                Log.e("TouristRouteRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: HttpException) {
            Log.e("TouristRouteRepository", "HTTP Error: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("TouristRouteRepository", "General Error: ${e.message}")
            null
        }
    }
}