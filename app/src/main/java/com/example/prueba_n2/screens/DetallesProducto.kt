package com.example.prueba_n2.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prueba_n2.model.Product

// Datos de ejemplo (luego vendrán de Firebase)
val dummyProducts = listOf(
    Product("1", "Chaqueta de Cuero", 89.99, 4.5f, "Chaqueta de cuero sintético, color negro."),
    Product("2", "Jeans Rotos", 45.50, 4.2f, "Jeans estilo 'destroyed' color azul claro."),
    Product("3", "Zapatillas Urbanas", 120.0, 4.8f, "Zapatillas blancas, perfectas para la ciudad.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesProducto(
    onNavigateToPublish: () -> Unit // Acción para ir a publicar
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Urban Shop") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToPublish) {
                // Puedes usar un ícono de "+" si lo importas
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(dummyProducts) { product ->
                TarjetaProducto(product = product)
            }
        }
    }
}