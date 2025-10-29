package com.example.prueba_n2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey
    val id: String,
    val name: String,
    val price: Int,
    val rating: Float,
    val description: String,
    val imageUri: String? = null,
    val imageResId: Int? = null,
    val sellerId: String,
    val timestamp: Long
)
