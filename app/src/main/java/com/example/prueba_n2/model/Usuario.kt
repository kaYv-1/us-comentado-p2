package com.example.prueba_n2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la estructura de datos de un usuario en la aplicación.
 * Es una entidad de Room y se almacenará en una tabla.
 */
@Entity(tableName = "usuarios")
data class Usuario(
    /**
     * La clave primaria del usuario. Se genera automáticamente por Room.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * El nombre del usuario.
     */
    val nombre: String,

    /**
     * El email del usuario, usado para el inicio de sesión.
     */
    val email: String,

    /**
     * La contraseña del usuario.
     */
    val contrasena: String
)
