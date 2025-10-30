package com.example.prueba_n2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prueba_n2.model.Producto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la entidad Producto.
 * Esta interfaz define los métodos que Room usará para interactuar con la tabla de productos.
 */
@Dao
interface ProductoDao {

    /**
     * Inserta un único producto en la base de datos.
     * Si ya existe un producto con la misma clave primaria, será reemplazado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: Producto)

    /**
     * Inserta una lista de productos. Se usa para poblar la base de datos con los datos iniciales.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<Producto>)

    /**
     * Obtiene todos los productos de la tabla, ordenados por su marca de tiempo de forma descendente.
     * Devuelve un `Flow`, lo que significa que la UI se actualizará automáticamente cada vez que los datos cambien.
     */
    @Query("SELECT * FROM productos ORDER BY timestamp DESC")
    fun getAllProductos(): Flow<List<Producto>>

    /**
     * Borra todos los productos de la tabla.
     */
    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}
