package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Producto
import com.example.prueba_n2.model.ProductoDao
import kotlinx.coroutines.flow.Flow

/**
 * El Repositorio para la entidad Producto.
 * Actúa como una capa de abstracción entre el ViewModel y la fuente de datos (en este caso, el DAO).
 */
class ProductoRepository(private val productoDao: ProductoDao) {

    /**
     * Expone la lista de todos los productos como un `Flow`.
     * El ViewModel observará este Flow para recibir actualizaciones automáticas cuando los datos cambien en la base de datos.
     */
    val allProductos: Flow<List<Producto>> = productoDao.getAllProductos()

    /**
     * Una función de suspensión que delega la inserción de un nuevo producto al DAO.
     * Se llama desde el ViewModel cuando el usuario publica un nuevo artículo.
     */
    suspend fun insertProducto(producto: Producto) {
        productoDao.insertProducto(producto)
    }
}
