package com.example.prueba_n2.ui.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prueba_n2.repository.ProductoRepository

class ProductoViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}