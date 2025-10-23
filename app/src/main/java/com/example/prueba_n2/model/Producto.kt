package com.example.prueba_n2.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val rating: Float = 0.0f,
    val description: String = "",
    val imageUrl: String = "", // Link a la imagen de la prenda
    val sellerId: String = "" // ID del usuario que la publicó
)