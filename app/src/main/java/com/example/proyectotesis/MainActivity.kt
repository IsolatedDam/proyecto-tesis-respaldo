package com.example.proyectotesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectotesis.ui.welcome.WelcomeScreen
import com.example.proyectotesis.ui.login.LoginScreen
import com.example.proyectotesis.ui.register.RegisterScreen
import com.example.proyectotesis.ui.profile.ProfileSettingsScreen
import com.example.proyectotesis.ui.welcome.WelcomeUserScreen
import com.example.proyectotesis.ui.mainmenu.MainMenuScreen
import com.example.proyectotesis.ui.login.ForgotPasswordScreen
import com.example.proyectotesis.ui.imagedetectionscreen.ImageDetectionScreen
import com.example.proyectotesis.ui.TouristRoutesScreen
import com.example.proyectotesis.data.famousRoutes
import com.example.proyectotesis.ui.TouristRouteDetailScreen
import com.example.proyectotesis.ui.theme.ProyectotesisTheme
import com.example.proyectotesis.ui.AccommodationScreen
import com.example.proyectotesis.data.recommendedAccommodations
import com.example.proyectotesis.ui.AccommodationDetailScreen // Asegúrate de tener esta pantalla para detalles de alojamiento

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProyectotesisTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "welcome",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("welcome") {
                            WelcomeScreen(onStartClick = {
                                navController.navigate("login")
                            })
                        }

                        composable("login") {
                            LoginScreen(
                                scope = coroutineScope,
                                onLoginSuccess = {
                                    navController.navigate("profile_settings")
                                },
                                onBackToLoginClick = {
                                    navController.navigate("register")
                                },
                                onForgotPasswordClick = {
                                    navController.navigate("forgot_password")
                                }
                            )
                        }

                        composable("register") {
                            RegisterScreen(
                                scope = coroutineScope,
                                onBackToLoginClick = {
                                    navController.navigate("login")
                                }
                            )
                        }

                        composable("profile_settings") {
                            ProfileSettingsScreen(
                                onProfileSaved = { username ->
                                    navController.navigate("welcome_user/$username")
                                }
                            )
                        }

                        composable("welcome_user/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
                            WelcomeUserScreen(username = username, navController = navController)
                        }

                        composable("main_menu") {
                            MainMenuScreen(
                                onImageDetectionClick = {
                                    navController.navigate("image_detection")
                                },
                                onTouristRoutesClick = {
                                    navController.navigate("tourist_routes")
                                },
                                onAccommodationClick = {
                                    navController.navigate("accommodation")
                                }
                            )
                        }

                        composable("forgot_password") {
                            ForgotPasswordScreen(
                                scope = coroutineScope,
                                onBackToLoginClick = {
                                    navController.navigate("login")
                                }
                            )
                        }

                        composable("image_detection") {
                            ImageDetectionScreen(
                                onCaptureImageClick = { /* Acción para capturar imagen */ },
                                onSelectImageClick = { /* Acción para seleccionar imagen */ },
                                onDetectClick = { /* Acción para iniciar detección */ }
                            )
                        }

                        composable("tourist_routes") {
                            TouristRoutesScreen(
                                routes = famousRoutes,
                                onRouteClick = { selectedRoute ->
                                    navController.navigate("tourist_route_detail/${selectedRoute.id}")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("tourist_route_detail/{routeId}") { backStackEntry ->
                            val routeId = backStackEntry.arguments?.getString("routeId")?.toIntOrNull()
                            val route = famousRoutes.find { it.id == routeId }
                            if (route != null) {
                                TouristRouteDetailScreen(
                                    route = route,
                                    onBack = { navController.popBackStack() }
                                )
                            } else {
                                navController.popBackStack()
                            }
                        }

                        // Pantalla de Alojamientos
                        composable("accommodation") {
                            AccommodationScreen(
                                accommodations = recommendedAccommodations,
                                onAccommodationClick = { selectedAccommodation ->
                                    navController.navigate("accommodation_detail/${selectedAccommodation.id}")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        // Pantalla de Detalle de Alojamiento
                        composable("accommodation_detail/{accommodationId}") { backStackEntry ->
                            val accommodationId = backStackEntry.arguments?.getString("accommodationId")?.toIntOrNull()
                            val accommodation = recommendedAccommodations.find { it.id == accommodationId }
                            if (accommodation != null) {
                                AccommodationDetailScreen(
                                    accommodation = accommodation,
                                    onBack = { navController.popBackStack() }
                                )
                            } else {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}