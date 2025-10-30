package com.example.prueba_n2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prueba_n2.repository.UsuarioRepository

/**
 * Una clase Fábrica (`Factory`) para crear instancias de `LoginViewModel`.
 * Es necesaria porque nuestro `LoginViewModel` tiene una dependencia en su constructor (el `UsuarioRepository`),
 * y esta clase le enseña al sistema cómo debe crearlo, inyectando esa dependencia.
 */
class LoginViewModelFactory(private val repository: UsuarioRepository) : ViewModelProvider.Factory {
    /**
     * Este método es llamado por el sistema cuando se necesita una instancia de un ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Comprueba si la clase que se solicita crear es una instancia de LoginViewModel.
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Si lo es, crea y devuelve una nueva instancia, pasándole el repositorio de usuarios.
            // `@Suppress("UNCHECKED_CAST")` es necesario porque el sistema no puede garantizar el tipo en tiempo de compilación,
            // pero nosotros sabemos que la conversión es segura gracias al `if` anterior.
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        // Si se solicita crear un ViewModel desconocido, lanza una excepción para notificar el error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
