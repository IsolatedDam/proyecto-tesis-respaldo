plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt") // Necesario para Room y Kapt
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectotesis"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace = "com.example.proyectotesis"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/LICENSE"
        }
    }

    buildFeatures {
        compose = true // Habilitar Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0" // Cambia a una versión compatible
    }
}

dependencies {
    // Core Android y AppCompat
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room y Coroutines
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2") // Kapt para Room
    implementation("androidx.room:room-ktx:2.5.2") // Para usar coroutines con Room

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.0")
    kapt("com.github.bumptech.glide:compiler:4.15.0")

    // Material Design
    implementation("com.google.android.material:material:1.10.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-graphics")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging y UI tools para Compose
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // Dependencias de testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Otras dependencias

    implementation("com.googlecode.libphonenumber:libphonenumber:8.12.48")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

}