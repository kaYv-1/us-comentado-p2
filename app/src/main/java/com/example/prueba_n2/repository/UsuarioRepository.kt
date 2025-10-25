package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.model.UsuarioDao

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun insertUsuario(usuario: Usuario) {
        usuarioDao.insertUsuario(usuario)
    }

    suspend fun getUsuarioByEmail(email: String): Usuario? {
        return usuarioDao.getUsuarioByEmail(email)
    }
}