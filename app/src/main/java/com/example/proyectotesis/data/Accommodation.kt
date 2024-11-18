package com.example.proyectotesis.data

/**
 * Representa un alojamiento con información relevante para mostrar en la aplicación.
 */
data class Accommodation(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val imageUrl: String
)