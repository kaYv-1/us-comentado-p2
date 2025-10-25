package com.example.prueba_n2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prueba_n2.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    // Función para insertar un solo producto (para PublicarProducto)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: Producto)

    // Función para insertar la lista inicial (para el Callback)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<Producto>)

    // Función para obtener todos los productos (para el feed)
    // (Volvemos a 'id DESC' para mostrar los más nuevos primero,
    // que es lo que tenías en tu archivo original)
    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun getAllProductos(): Flow<List<Producto>>

    // Función para borrar todo (para el Callback)
    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}
