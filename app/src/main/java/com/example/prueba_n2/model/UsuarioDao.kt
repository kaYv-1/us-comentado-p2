package com.example.prueba_n2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertUsuario(usuario: Usuario) // Sigue funcionando

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioByEmail(email: String): Usuario? // Sigue funcionando

}