package com.example.prueba_n2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prueba_n2.R // Importante: Asegúrate que esta sea tu R
import com.example.prueba_n2.model.Product

@Composable
fun TarjetaProducto(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column {
            // Imagen de la prenda (usamos un placeholder que ya existe)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Cambia esto por la imagen real
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // Nombre y Precio
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(product.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        "$${product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Calificación
                Text("Calificación: ${product.rating} ★", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // Descripción
                Text(product.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}