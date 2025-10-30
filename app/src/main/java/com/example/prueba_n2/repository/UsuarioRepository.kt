package com.example.prueba_n2.repository

import com.example.prueba_n2.model.Usuario
import com.example.prueba_n2.model.UsuarioDao

/**
 * El Repositorio para la entidad Usuario.
 * Sirve como intermediario entre el LoginViewModel y el UsuarioDao.
 */
class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    /**
     * Delega la inserción de un nuevo usuario al DAO.
     * Se llama desde el LoginViewModel durante el proceso de registro.
     */
    suspend fun insertUsuario(usuario: Usuario) {
        usuarioDao.insertUsuario(usuario)
    }

    /**
     * Delega la búsqueda de un usuario por su email al DAO.
     * Se llama desde el LoginViewModel para verificar las credenciales durante el inicio de sesión.
     */
    suspend fun getUsuarioByEmail(email: String): Usuario? {
        return usuarioDao.getUsuarioByEmail(email)
    }
}