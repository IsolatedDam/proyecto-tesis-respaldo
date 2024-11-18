package com.example.proyectotesis.di

import com.example.proyectotesis.network.PersonaService
import com.example.proyectotesis.repository.PersonaRepository
import com.example.proyectotesis.viewmodel.PersonaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    // Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.102:5000/") // Cambia esta URL base según tu servidor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // PersonaService instance
    single {
        get<Retrofit>().create(PersonaService::class.java)
    }

    // PersonaRepository instance
    single {
        PersonaRepository(get()) // Pasa PersonaService al constructor
    }

    // PersonaViewModel instance
    viewModel {
        PersonaViewModel(get()) // Pasa PersonaRepository al constructor
    }
}