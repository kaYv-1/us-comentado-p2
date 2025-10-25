// C:/Users/israe/AndroidStudioProjects/UrbanShop/app/src/main/java/com/example/prueba_n2/ui/login/LoginViewModel.kt

package com.example.prueba_n2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Empty : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
// --- FIN DE LA CORRECCIÓN ---


class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    // Ahora que LoginState está definido, esta línea es válida.
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser.asStateFlow()

    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val usuario = repository.getUsuarioByEmail(email)
                if (usuario != null && usuario.contrasena == contrasena) {
                    // Esta línea ahora es válida.
                    _loginState.value = LoginState.Success
                    _currentUser.value = usuario // Assign the logged-in user
                } else {
                    // Esta línea ahora es válida.
                    _loginState.value = LoginState.Error("Email o contraseña incorrectos")
                    _currentUser.value = null
                }
            } catch (e: Exception) {
                // Esta línea ahora es válida.
                _loginState.value = LoginState.Error("Error al iniciar sesión: ${e.message}")
                _currentUser.value = null
            }
        }
    }

    fun registrarUsuario(nombre: String, email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                // Lógica de registro (ejemplo)
                if (repository.getUsuarioByEmail(email) != null) {
                    _loginState.value = LoginState.Error("El email ya está registrado.")
                    return@launch
                }
                val nuevoUsuario = Usuario(nombre = nombre, email = email, contrasena = contrasena)
                repository.insertUsuario(nuevoUsuario)
                // Opcional: Iniciar sesión automáticamente después del registro
                _loginState.value = LoginState.Success
                _currentUser.value = nuevoUsuario
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error durante el registro: ${e.message}")
            }
        }
    }

    fun logout() {
        _currentUser.value = null // Clear the user on logout
        // Esta línea ahora es válida.
        _loginState.value = LoginState.Empty
    }
}
