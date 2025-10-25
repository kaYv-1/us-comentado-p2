package com.example.prueba_n2.ui.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_n2.model.Producto
import com.example.prueba_n2.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {

    // Expone los productos como StateFlow para observar en la UI
    val productos: StateFlow<List<Producto>> = repository.allProductos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // Mantiene activo 5s
            initialValue = emptyList() // Valor inicial mientras carga
        )

    // Función para añadir un nuevo producto
    fun addProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insertProducto(producto)
        }
    }
}