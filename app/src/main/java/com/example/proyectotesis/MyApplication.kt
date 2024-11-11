package com.example.proyectotesis

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // No es necesario inicializar ThreeTenABP
        // Ya que estamos utilizando java.time directamente
    }
}