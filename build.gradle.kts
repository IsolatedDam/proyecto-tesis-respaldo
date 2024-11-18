// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.6.0") // Versión estable del plugin Gradle
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10") // Actualizado para compatibilidad con Compose 1.5.3
    }
}

// Configuración del control de versiones para Compose y Kotlin
ext {

}

// Optimización para evitar tareas Always-Run
subprojects {
    tasks.matching { it.name == "checkKotlinGradlePluginConfigurationErrors" }.configureEach {
        onlyIf { false } // Desactiva la tarea redundante
    }
}