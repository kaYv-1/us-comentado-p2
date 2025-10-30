package com.example.prueba_n2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la estructura de datos de un producto en la aplicación.
 * Es una entidad de Room y se almacenará en una tabla.
 */
@Entity(tableName = "productos")
data class Producto(
    /**
     * La clave primaria única para cada producto.
     */
    @PrimaryKey
    val id: String,

    /**
     * El nombre del producto.
     */
    val name: String,

    /**
     * El precio del producto.
     */
    val price: Int,

    /**
     * La calificación del producto.
     */
    val rating: Float,

    /**
     * Una descripción detallada del producto.
     */
    val description: String,

    /**
     * La ruta (URI) de la imagen como un String. Se usa para imágenes de la galería.
     */
    val imageUri: String? = null,

    /**
     * El ID del recurso de imagen. Se usa para las imágenes predeterminadas.
     */
    val imageResId: Int? = null,

    /**
     * El ID del usuario que ha publicado el producto.
     */
    val sellerId: String,

    /**
     * Una marca de tiempo que registra cuándo se creó el producto.
     */
    val timestamp: Long
)
