package com.example.proyectotesis.network

import com.example.proyectotesis.data.TouristRoute
import retrofit2.Response
import retrofit2.http.GET

interface TouristRouteService {
    @GET("tourist_routes") // Reemplaza con el endpoint de tu API
    suspend fun getAllRoutes(): Response<List<TouristRoute>>  // Cambié de Call a Response
}