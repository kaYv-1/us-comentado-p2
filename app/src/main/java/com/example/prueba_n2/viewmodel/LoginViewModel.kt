package com.example.prueba_n2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Una clase sellada (`sealed class`) que representa los diferentes estados posibles del proceso de inicio de sesión.
 * La UI puede reaccionar a estos estados para mostrar mensajes de éxito, error o un estado neutro.
 */
sealed class LoginState {
    object Empty : LoginState() // Estado inicial o después de cerrar sesión.
    object Success : LoginState() // Estado cuando el login o registro es exitoso.
    data class Error(val message: String) : LoginState() // Estado cuando ocurre un error, con un mensaje descriptivo.
}

/**
 * El ViewModel para las pantallas de Login y Registro.
 * Contiene toda la lógica de negocio para la autenticación de usuarios.
 */
class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    // Un StateFlow mutable y privado para gestionar el estado del login internamente.
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    // La versión pública e inmutable del StateFlow, para que la UI lo observe de forma segura.
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // Un StateFlow para mantener al usuario que ha iniciado sesión.
    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser.asStateFlow()

    /**
     * Intenta iniciar sesión con el email y la contraseña proporcionados.
     */
    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val usuario = repository.getUsuarioByEmail(email)
                // Comprueba si el usuario existe y si la contraseña coincide.
                if (usuario != null && usuario.contrasena == contrasena) {
                    _loginState.value = LoginState.Success
                    _currentUser.value = usuario // Guarda el usuario actual
                } else {
                    _loginState.value = LoginState.Error("Email o contraseña incorrectos")
                    _currentUser.value = null
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error al iniciar sesión: ${e.message}")
                _currentUser.value = null
            }
        }
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     */
    fun registrarUsuario(nombre: String, email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                // Primero, comprueba si el email ya está en uso para evitar duplicados.
                if (repository.getUsuarioByEmail(email) != null) {
                    _loginState.value = LoginState.Error("El email ya está registrado.")
                    return@launch // Detiene la ejecución si el email ya existe.
                }
                val nuevoUsuario = Usuario(nombre = nombre, email = email, contrasena = contrasena)
                repository.insertUsuario(nuevoUsuario)

                // Después de un registro exitoso, inicia sesión automáticamente con el nuevo usuario.
                _loginState.value = LoginState.Success
                _currentUser.value = nuevoUsuario
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error durante el registro: ${e.message}")
            }
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        _currentUser.value = null // Limpia el usuario guardado.
        _loginState.value = LoginState.Empty // Restablece el estado de login.
    }
}
