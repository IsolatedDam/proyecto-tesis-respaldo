package com.example.proyectotesis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.proyectotesis.data.TouristRoute
import com.example.proyectotesis.repository.TouristRouteRepository
import kotlinx.coroutines.launch

class TouristRouteViewModel(private val touristRouteRepository: TouristRouteRepository) : ViewModel() {

    private val _routes = MutableLiveData<List<TouristRoute>>()
    val routes: LiveData<List<TouristRoute>> get() = _routes

    // Obtener todas las rutas turísticas
    fun getAllRoutes() {
        viewModelScope.launch {
            val result = touristRouteRepository.getAllRoutes()  // Llamada suspendida
            _routes.value = result ?: emptyList()
        }
    }
}