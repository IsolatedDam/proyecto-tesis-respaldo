package com.example.proyectotesis.network

import retrofit2.Response
import retrofit2.http.*

interface PersonaService {

    @GET("personas")
    suspend fun getAllPersonas(): Response<ResponseStatus<List<AppUser>>>

    @GET("persona/{id}")
    suspend fun getPersonaById(@Path("id") id: Int): Response<ResponseStatus<AppUser>>

    @POST("create_persona")
    suspend fun createPersona(@Body persona: Map<String, String>): Response<ResponseStatus<String>>

    @PUT("update_persona/{id}")
    suspend fun updatePersona(@Path("id") id: Int, @Body personaData: Map<String, Any>): Response<ResponseStatus<String>>

    @DELETE("delete_persona/{id}")
    suspend fun deletePersona(@Path("id") id: Int): Response<ResponseStatus<String>>

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): Response<LoginResponse>

    @POST("perfil_usuario")
    suspend fun saveUserProfile(@Body profileData: Map<String, String>): Response<ResponseStatus<String>>

}