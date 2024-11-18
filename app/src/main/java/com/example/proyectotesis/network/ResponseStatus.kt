package com.example.proyectotesis.network

data class ResponseStatus<T>(
    val status: String,
    val data: T?,
    val message: String? = null
)
