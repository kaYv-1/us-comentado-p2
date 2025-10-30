package com.example.prueba_n2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prueba_n2.model.AppDatabase
import com.example.prueba_n2.repository.ProductoRepository
import com.example.prueba_n2.repository.UsuarioRepository
import com.example.prueba_n2.ui.screens.DetallesProducto
import com.example.prueba_n2.ui.screens.PantallaIngreso
import com.example.prueba_n2.ui.screens.PantallaPerfil
import com.example.prueba_n2.ui.screens.PantallaRegistro
import com.example.prueba_n2.ui.screens.PublicarProducto
import com.example.prueba_n2.ui.theme.Prueba_n2Theme
import com.example.prueba_n2.viewmodel.LoginViewModel
import com.example.prueba_n2.viewmodel.LoginViewModelFactory
import com.example.prueba_n2.viewmodel.ProductoViewModel
import com.example.prueba_n2.viewmodel.ProductoViewModelFactory

/**
 * La actividad principal y único punto de entrada de la aplicación.
 */
class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this, lifecycleScope) }
    private val usuarioRepository by lazy { UsuarioRepository(database.usuarioDao()) }
    private val productoRepository by lazy { ProductoRepository(database.productoDao()) }

    private val loginViewModelFactory by lazy { LoginViewModelFactory(usuarioRepository) }
    private val productoViewModelFactory by lazy { ProductoViewModelFactory(productoRepository) }

    private val loginViewModel: LoginViewModel by viewModels { loginViewModelFactory }
    private val productoViewModel: ProductoViewModel by viewModels { productoViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prueba_n2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(loginViewModel, productoViewModel)
                }
            }
        }
    }
}

/**
 * Un objeto que contiene las constantes de las rutas de navegación.
 */
object AppRoutes {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val PRINCIPAL = "principal"
    const val PUBLICAR = "publicar"
    const val PERFIL = "perfil"
}

/**
 * El componente Composable que gestiona toda la navegación de la aplicación.
 */
@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    productoViewModel: ProductoViewModel
) {
    val navController = rememberNavController()
    val currentUser by loginViewModel.currentUser.collectAsState()
    val productos by productoViewModel.productos.collectAsState()

    NavHost(navController = navController, startDestination = AppRoutes.LOGIN) {

        composable(AppRoutes.LOGIN) {
            val loginState by loginViewModel.loginState.collectAsState()
            PantallaIngreso(
                loginState = loginState,
                onLogin = loginViewModel::login,
                onNavigateToRegister = { navController.navigate(AppRoutes.REGISTRO) },
                onLoginSuccess = {
                    navController.navigate(AppRoutes.PRINCIPAL) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.REGISTRO) {
            PantallaRegistro(
                onRegister = { nombre, email, pass ->
                    loginViewModel.registrarUsuario(nombre, email, pass)
                    navController.popBackStack()
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.PRINCIPAL) {
            DetallesProducto(
                productos = productos,
                onNavigateToPublish = { navController.navigate(AppRoutes.PUBLICAR) },
                onNavigateToProfile = { navController.navigate(AppRoutes.PERFIL) }
            )
        }

        composable(AppRoutes.PUBLICAR) {
            PublicarProducto(
                currentUser = currentUser,
                onProductoPublicado = { nuevoProducto ->
                    productoViewModel.addProducto(nuevoProducto)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.PERFIL) {
            PantallaPerfil(
                usuario = currentUser,
                onLogout = {
                    loginViewModel.logout()
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
