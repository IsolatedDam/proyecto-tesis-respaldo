package com.example.proyectotesis

import android.app.Application
import com.example.proyectotesis.di.networkModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicia Koin
        startKoin {
            // Contexto de la aplicación
            androidContext(this@MyApplication)
            // Cargar módulos de Koin
            modules(networkModule)
        }
    }
}