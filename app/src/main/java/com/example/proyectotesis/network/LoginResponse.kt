package com.example.proyectotesis.network

data class LoginResponse(
    val status: String,
    val message: String?,
    val user: AppUser?
)
