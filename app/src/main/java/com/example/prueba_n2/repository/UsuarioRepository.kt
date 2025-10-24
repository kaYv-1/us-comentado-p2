package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.model.UsuarioDao

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun insert(usuario: Usuario) {
        usuarioDao.insert(usuario)
    }

    suspend fun getUsuarioPorEmail(email: String): Usuario? {
        return usuarioDao.getUsuarioPorEmail(email)
    }
}