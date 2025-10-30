package com.example.prueba_n2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_n2.model.Producto
import com.example.prueba_n2.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * El ViewModel para la pantalla principal (el FYP).
 * Se encarga de la lógica de negocio relacionada con los productos.
 */
class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {

    /**
     * Expone la lista de productos a la interfaz de usuario como un `StateFlow`.
     * `StateFlow` es un tipo de `Flow` que siempre tiene un valor y emite actualizaciones de estado.
     * La UI observará este `StateFlow` para redibujarse automáticamente cuando la lista de productos cambie.
     * `stateIn` convierte un `Flow` normal en un `StateFlow` de forma eficiente, compartiendo la suscripción
     * entre múltiples observadores dentro del `viewModelScope`.
     */
    val productos: StateFlow<List<Producto>> = repository.allProductos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // Mantiene la suscripción activa 5s después de que la UI deja de observar
            initialValue = emptyList() // Valor inicial mientras se cargan los datos
        )

    /**
     * Lanza una corrutina en el `viewModelScope` para insertar un nuevo producto en la base de datos.
     * Llama a la función correspondiente en el repositorio.
     */
    fun addProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insertProducto(producto)
        }
    }
}