// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

// Asegúrate de que los repositorios se declaren aquí también
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
        classpath("com.android.tools.build:gradle:8.6.0") // Versión estable de Gradle
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10") // Versión más reciente y compatible de Kotlin
    }
}

ext {
    var compose_version = "1.5.2" // Define la versión de Compose aquí
}
