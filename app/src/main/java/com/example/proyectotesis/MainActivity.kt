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
import com.example.proyectotesis.ui.login.LoginScreen
import com.example.proyectotesis.ui.mainmenu.MainMenuScreen
import com.example.proyectotesis.ui.profile.ProfileSettingsScreen
import com.example.proyectotesis.ui.register.RegisterScreen
import com.example.proyectotesis.ui.theme.ProyectotesisTheme
import com.example.proyectotesis.ui.welcome.WelcomeScreen
import com.example.proyectotesis.ui.welcome.WelcomeUserScreen
import com.example.proyectotesis.ui.login.ForgotPasswordScreen

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
                        // Pantalla de Bienvenida
                        composable("welcome") {
                            WelcomeScreen(onStartClick = {
                                navController.navigate("login")
                            })
                        }

                        // Pantalla de Iniciar Sesión
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

                        // Pantalla de Crear Cuenta
                        composable("register") {
                            RegisterScreen(
                                scope = coroutineScope,
                                onBackToLoginClick = {
                                    navController.navigate("login")
                                }
                            )
                        }

                        // Pantalla de Configuración de Perfil
                        composable("profile_settings") {
                            ProfileSettingsScreen(
                                onProfileSaved = { username ->
                                    navController.navigate("welcome_user/$username")
                                }
                            )
                        }

                        // Pantalla de Usuario Bienvenido
                        composable("welcome_user/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
                            WelcomeUserScreen(username = username, navController = navController)
                        }

                        // Pantalla del Menú Principal
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

                        // Pantalla de Recuperación de Contraseña
                        composable("forgot_password") {
                            ForgotPasswordScreen(
                                scope = coroutineScope,
                                onBackToLoginClick = {
                                    navController.navigate("login")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}