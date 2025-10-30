package com.example.prueba_n2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prueba_n2.repository.ProductoRepository

/**
 * Una clase Fábrica (`Factory`) para crear instancias de `ProductoViewModel`.
 * Es necesaria porque nuestro `ProductoViewModel` tiene una dependencia en su constructor (el `ProductoRepository`),
 * y esta clase le enseña al sistema cómo debe crearlo, inyectando esa dependencia.
 */
class ProductoViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
    /**
     * Este método es llamado por el sistema cuando se necesita una instancia de un ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Comprueba si la clase que se solicita crear es una instancia de ProductoViewModel.
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            // Si lo es, crea y devuelve una nueva instancia de ProductoViewModel, pasándole el repositorio.
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        // Si se solicita crear un ViewModel desconocido, lanza una excepción para notificar el error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}