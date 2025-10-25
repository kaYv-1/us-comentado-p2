package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Producto
import com.example.prueba_n2.model.ProductoDao
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    // Expone el Flow de todos los productos desde el DAO
    val allProductos: Flow<List<Producto>> = productoDao.getAllProductos()

    // Función suspendida para insertar un producto
    suspend fun insertProducto(producto: Producto) {
        productoDao.insertProducto(producto)
    }

    // Aquí podrías añadir funciones para delete, update, etc.
}