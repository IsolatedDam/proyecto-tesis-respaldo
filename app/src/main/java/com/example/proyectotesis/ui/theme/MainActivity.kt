package com.example.proyectotesis.ui.theme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectotesis.ui.*
import com.example.proyectotesis.ui.imagedetectionscreen.ImageDetectionScreen
import com.example.proyectotesis.ui.login.ForgotPasswordScreen
import com.example.proyectotesis.ui.login.LoginScreen
import com.example.proyectotesis.ui.mainmenu.MainMenuScreen
import com.example.proyectotesis.ui.profile.ProfileSettingsScreen
import com.example.proyectotesis.ui.register.RegisterScreen
import com.example.proyectotesis.ui.welcome.WelcomeUserScreen
import com.example.proyectotesis.viewmodel.PersonaViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProyectotesisTheme {
                val navController = rememberNavController()
                val personaViewModel: PersonaViewModel = getViewModel()
                val isLoading by personaViewModel.isLoading.collectAsState()

                MainActivityContent(
                    navController = navController,
                    isLoading = isLoading,
                    personaViewModel = personaViewModel
                )
            }
        }
    }
}

@Composable
fun MainActivityContent(
    navController: NavHostController,
    isLoading: Boolean,
    personaViewModel: PersonaViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = Routes.LOGIN,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.LOGIN) {
                    LoginScreen(
                        viewModel = personaViewModel,
                        onLoginSuccess = {
                            val authenticatedUser = personaViewModel.authenticatedUser.value
                            if (authenticatedUser != null) {
                                navController.navigate(
                                    Routes.PROFILE_SETTINGS.replace("{username}", authenticatedUser.nombre)
                                )
                            }
                        },
                        onForgotPasswordClick = { navController.navigate(Routes.FORGOT_PASSWORD) },
                        onRegisterClick = { navController.navigate(Routes.REGISTER) }
                    )
                }

                composable(Routes.REGISTER) {
                    RegisterScreen(
                        personaViewModel = personaViewModel,
                        onRegisterSuccess = { navController.navigate(Routes.LOGIN) },
                        onBackToLoginClick = { navController.navigate(Routes.LOGIN) }
                    )
                }

                composable(
                    route = Routes.PROFILE_SETTINGS,
                    arguments = listOf(navArgument("username") { type = NavType.StringType })
                ) { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username") ?: ""
                    ProfileSettingsScreen(
                        initialUsername = username,
                        personaViewModel = personaViewModel,
                        onProfileSaved = { updatedUsername ->
                            navController.navigate(
                                Routes.WELCOME_USER.replace("{username}", updatedUsername)
                            )
                        }
                    )
                }

                composable(
                    route = Routes.WELCOME_USER,
                    arguments = listOf(navArgument("username") { type = NavType.StringType })
                ) { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username") ?: ""
                    WelcomeUserScreen(
                        username = username,
                        navController = navController
                    )
                }

                composable(Routes.MAIN_MENU) {
                    val userRole by personaViewModel.userRole.collectAsState()

                    MainMenuScreen(
                        onProfileSettingsClick = { navController.navigate(Routes.PROFILE_SETTINGS) },
                        onImageDetectionClick = { navController.navigate(Routes.IMAGE_DETECTION) },
                        onTouristRoutesClick = { navController.navigate(Routes.TOURIST_ROUTES) },
                        onAccommodationClick = { navController.navigate(Routes.ACCOMMODATION) },
                        onUserManagementClick = {
                            if (userRole == "admin") {
                                navController.navigate(Routes.ADMIN_USER_MANAGEMENT)
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Acceso denegado: Solo para administradores",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        personaViewModel = personaViewModel
                    )
                }

                composable(Routes.ADMIN_USER_MANAGEMENT) {
                    AdminUserManagementScreen(
                        personaViewModel = personaViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable(Routes.FORGOT_PASSWORD) {
                    ForgotPasswordScreen(onBackToLoginClick = { navController.navigate(Routes.LOGIN) })
                }

                composable(Routes.IMAGE_DETECTION) {
                    ImageDetectionScreen(
                        onCaptureImageClick = { /* Acción para capturar imagen */ },
                        onSelectImageClick = { /* Acción para seleccionar imagen */ },
                        onDetectClick = { /* Acción para iniciar detección */ }
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}