package com.example.prueba_n2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.prueba_n2.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun getUsuarioPorEmail(email: String): Usuario?
}