package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Producto
import com.example.prueba_n2.model.ProductoDao
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    val allProductos: Flow<List<Producto>> = productoDao.getAllProductos()

    suspend fun insertProducto(producto: Producto) {
        productoDao.insertProducto(producto)
    }
}
