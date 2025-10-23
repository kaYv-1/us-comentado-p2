package com.example.prueba_n2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.prueba_n2.screens.DetallesProducto
import com.example.prueba_n2.screens.PantallaIngreso
import com.example.prueba_n2.screens.PantallaRegistro
import com.example.prueba_n2.ui.theme.Prueba_n2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prueba_n2Theme { // 👈 CAMBIO DE TEMA
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// Objeto para definir las rutas
object AppRoutes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PRINCIPAL = "principal" // 👈 RUTA CAMBIADA (antes FEED)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.LOGIN) {

        // Pantalla de Login
        composable(AppRoutes.LOGIN) {
            PantallaIngreso( // 👈 LLAMADA CAMBIADA
                onLoginClick = { email, password ->
                    // --- Lógica de Firebase ---
                    navController.navigate(AppRoutes.PRINCIPAL) { // 👈 NAVEGACIÓN CAMBIADA
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        // Pantalla de Registro
        composable(AppRoutes.REGISTER) {
            PantallaRegistro( // 👈 LLAMADA CAMBIADA
                onRegisterClick = { nombre, telefono, email, password ->
                    // --- Lógica de Firebase ---
                    navController.navigate(AppRoutes.PRINCIPAL) { // 👈 NAVEGACIÓN CAMBIADA
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla Principal (Feed)
        composable(AppRoutes.PRINCIPAL) { // 👈 RUTA CAMBIADA
            DetallesProducto( // 👈 LLAMADA CAMBIADA
                onNavigateToPublish = {
                    // navController.navigate(AppRoutes.PUBLISH)
                }
            )
        }
    }
}