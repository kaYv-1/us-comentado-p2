package com.example.prueba_n2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prueba_n2.model.Producto
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.res.stringResource
import com.example.prueba_n2.R

val dummyProductos = listOf(
    Producto(
        id = "1",
        name = "Chaqueta de Cuero",
        price = 89.99,
        imageUrl = "https://via.placeholder.com/150/000000/FFFFFF/?text=Chaqueta", // URL de ejemplo
        description = "Chaqueta de cuero sintético, color negro.",
        rating = 4.5f,
        sellerId = "user123" // ID de vendedor de ejemplo
    ),
    Producto(
        id = "2",
        name = "Jeans Rotos",
        price = 45.50,
        imageUrl = "https://via.placeholder.com/150/0000FF/FFFFFF/?text=Jeans", // URL de ejemplo
        description = "Jeans estilo 'destroyed' color azul claro.",
        rating = 4.2f,
        sellerId = "user456" // ID de vendedor de ejemplo
    ),
    Producto(
        id = "3",
        name = "Zapatillas Urbanas",
        price = 120.0,
        imageUrl = "https://via.placeholder.com/150/FFFFFF/000000/?text=Zapatillas", // URL de ejemplo
        description = "Zapatillas blancas, perfectas para la ciudad.",
        rating = 4.8f,
        sellerId = "user123" // ID de vendedor de ejemplo
    )
)
// --- FIN DE LA CORRECCIÓN ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesProducto(
    productos: List<Producto>,
    onNavigateToPublish: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Mi Perfil"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToPublish) {
                // He añadido el icono de "+" para que sea más visual.
                Icon(Icons.Default.Add, contentDescription = "Publicar Producto")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(productos) { producto ->
                // Asumiendo que TarjetaProducto también está actualizada para usar los nuevos campos.
                TarjetaProducto(producto = producto)
            }
        }
    }
}
