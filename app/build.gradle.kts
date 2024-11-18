plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // Plugin necesario para el Compose Compiler
}

android {
    namespace = "com.example.proyectotesis"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyectotesis"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Actualizado
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Core Android y Jetpack Libraries
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7") // Corregido para mantener compatibilidad

    // DataStore
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2024.11.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.5") // Actualizado

    // Material Design y otros componentes
    implementation("androidx.compose.material:material:1.7.5")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // Koin Dependency Injection
    implementation("io.insert-koin:koin-android:3.4.1")
    implementation("io.insert-koin:koin-android-compat:3.4.1")
    implementation("io.insert-koin:koin-core:3.4.1")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")

    // Retrofit y Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.firebase.firestore.ktx)

    // Debugging Tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-tooling-preview")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // BCrypt para contraseñas
    implementation("org.mindrot:jbcrypt:0.4")

    //Otras dependencias

    implementation("androidx.media3:media3-common-ktx:1.5.0-rc01")
}