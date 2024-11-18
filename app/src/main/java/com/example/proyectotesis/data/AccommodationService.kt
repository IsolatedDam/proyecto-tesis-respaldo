package com.example.proyectotesis.network

import com.example.proyectotesis.data.Accommodation
import retrofit2.Response
import retrofit2.http.*

interface AccommodationService {

    @GET("accommodations")
    suspend fun getAllAccommodations(): Response<List<Accommodation>>

    @POST("accommodation")
    suspend fun createAccommodation(@Body accommodation: Accommodation): Response<Accommodation>

    @PUT("accommodation/{id}")
    suspend fun updateAccommodation(@Path("id") id: Int, @Body accommodation: Accommodation): Response<Accommodation>

    @DELETE("accommodation/{id}")
    suspend fun deleteAccommodation(@Path("id") id: Int): Response<Void>
}