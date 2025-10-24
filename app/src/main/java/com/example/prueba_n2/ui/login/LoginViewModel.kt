package com.example.prueba_n2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun registrarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            if (repository.getUsuarioPorEmail(usuario.email) != null) {
                _loginState.value = LoginState.Error("El usuario ya existe")
            } else {
                repository.insert(usuario)
                _loginState.value = LoginState.Success
            }
        }
    }

    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            val usuario = repository.getUsuarioPorEmail(email)
            if (usuario == null) {
                _loginState.value = LoginState.Error("Cuenta no registrada")
            } else if (usuario.contrasena == contrasena) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Contraseña incorrecta")
            }
        }
    }
}

sealed class LoginState {
    object Empty : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}