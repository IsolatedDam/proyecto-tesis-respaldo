package com.example.proyectotesis.data

// Repositorio temporal que almacena los usuarios en memoria
object LocalUserRepository {
    private val users = mutableListOf<User>()

    // Función para agregar un nuevo usuario
    fun insertUser(user: User) {
        users.add(user)
    }

    // Función para buscar un usuario por nombre de usuario y contraseña
    fun getUser(username: String, password: String): User? {
        return users.find { it.username == username && it.password == password }
    }
}

// Definición simple de la clase User
data class User(
    val username: String,
    val email: String,
    val password: String
)