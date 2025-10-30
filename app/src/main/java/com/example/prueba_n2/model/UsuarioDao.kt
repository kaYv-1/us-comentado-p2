package com.example.prueba_n2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object (DAO) para la entidad Usuario.
 * Define los métodos para interactuar con la tabla de usuarios en la base de datos.
 */
@Dao
interface UsuarioDao {
    /**
     * Inserta un nuevo usuario en la base de datos.
     * Esta función se utiliza durante el proceso de registro.
     */
    @Insert
    suspend fun insertUsuario(usuario: Usuario)

    /**
     * Busca y devuelve un usuario a partir de su dirección de email.
     * El email es único para cada usuario, por lo que esta consulta devuelve un solo resultado o ninguno.
     * Es fundamental para el proceso de inicio de sesión, para verificar si un usuario existe y obtener sus datos.
     */
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioByEmail(email: String): Usuario?
}