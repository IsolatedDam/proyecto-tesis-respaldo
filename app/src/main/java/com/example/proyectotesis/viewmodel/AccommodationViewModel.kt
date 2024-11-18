package com.example.proyectotesis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.proyectotesis.data.Accommodation
import com.example.proyectotesis.repository.AccommodationRepository
import kotlinx.coroutines.launch

class AccommodationViewModel(private val accommodationRepository: AccommodationRepository) : ViewModel() {

    private val _accommodations = MutableLiveData<List<Accommodation>>()
    val accommodations: LiveData<List<Accommodation>> get() = _accommodations

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage

    // Obtener todos los alojamientos
    fun getAllAccommodations() {
        viewModelScope.launch {
            try {
                val result = accommodationRepository.getAllAccommodations() // Llamada suspendida
                _accommodations.value = result ?: emptyList()
            } catch (e: Exception) {
                _statusMessage.value = "Error al obtener los alojamientos: ${e.message}"
            }
        }
    }

    // Crear un nuevo alojamiento
    fun createAccommodation(accommodation: Accommodation) {
        viewModelScope.launch {
            try {
                val result = accommodationRepository.createAccommodation(accommodation) // Llamada suspendida
                if (result != null) {
                    _statusMessage.value = "Alojamiento creado exitosamente"
                    getAllAccommodations()  // Actualiza la lista después de crear
                } else {
                    _statusMessage.value = "Error al crear el alojamiento"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al crear el alojamiento: ${e.message}"
            }
        }
    }

    // Actualizar un alojamiento
    fun updateAccommodation(id: Int, accommodation: Accommodation) {
        viewModelScope.launch {
            try {
                val result = accommodationRepository.updateAccommodation(id, accommodation) // Llamada suspendida
                if (result != null) {
                    _statusMessage.value = "Alojamiento actualizado exitosamente"
                    getAllAccommodations()  // Actualiza la lista después de actualizar
                } else {
                    _statusMessage.value = "Error al actualizar el alojamiento"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al actualizar el alojamiento: ${e.message}"
            }
        }
    }

    // Eliminar un alojamiento
    fun deleteAccommodation(id: Int) {
        viewModelScope.launch {
            try {
                val success = accommodationRepository.deleteAccommodation(id) // Llamada suspendida
                if (success) {
                    _statusMessage.value = "Alojamiento eliminado exitosamente"
                    getAllAccommodations()  // Actualiza la lista después de eliminar
                } else {
                    _statusMessage.value = "Error al eliminar el alojamiento"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error al eliminar el alojamiento: ${e.message}"
            }
        }
    }
}