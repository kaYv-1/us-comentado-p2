package com.example.prueba_n2.model

import com.example.prueba_n2.R
import java.util.UUID

/**
 * Define una lista de productos predeterminados para poblar la base de datos inicial.
 */
val dummyProductos = listOf(
    Producto(
        id = UUID.randomUUID().toString(),
        name = "Zapatillas Puma Suede",
        price = 64990,
        rating = 4.8f,
        description = "Un ícono del estilo urbano. Las Puma Suede son un clásico atemporal que combina con todo, perfectas para un look casual y cómodo.",
        imageResId = R.drawable.producto_zapatillas,
        sellerId = "tienda_oficial",
        timestamp = System.currentTimeMillis() - 2 * 60 * 1000 // Hace 2 minutos
    ),
    Producto(
        id = UUID.randomUUID().toString(),
        name = "Polerón Oversized Cierre",
        price = 49990,
        description = "Polerón negro con cierre y capucha, de calce oversized. Ideal para un estilo relajado y moderno. Hecho de algodón de alta calidad.",
        imageResId = R.drawable.producto_poleron,
        rating = 4.6f,
        sellerId = "urban_style_cl",
        timestamp = System.currentTimeMillis() - 1 * 60 * 1000 // Hace 1 minuto
    ),
    Producto(
        id = UUID.randomUUID().toString(),
        name = "Jeans Anchos (Baggy)",
        price = 59990,
        description = "Jeans de estilo baggy con un lavado claro. La prenda perfecta para un look de los 90s, cómodos y con mucho estilo.",
        imageResId = R.drawable.producto_jeans,
        rating = 4.7f,
        sellerId = "denim_masters",
        timestamp = System.currentTimeMillis() // Ahora
    )
)
