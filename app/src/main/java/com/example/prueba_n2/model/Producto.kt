package com.example.prueba_n2.model

import androidx.room.Entity // ✅ AÑADIR
import androidx.room.PrimaryKey // ✅ AÑADIR

@Entity(tableName = "productos") // ✅ AÑADIR (nombre de la tabla)
data class Producto(
    @PrimaryKey // ✅ AÑADIR (id será la clave primaria)
    val id: String, // Mantenemos String para UUID
    val name: String,
    val price: Double,
    val rating: Float,
    val description: String,
    val imageUrl: String?, // Puede ser null si no se selecciona imagen
    val sellerId: String // Podría vincularse al Usuario ID en el futuro
)