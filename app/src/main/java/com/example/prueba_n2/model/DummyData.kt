package com.example.prueba_n2.model

import java.util.UUID

// Lista de productos de prueba que se insertarán en la base de datos
val dummyProductos = listOf(
    Producto(
        id = UUID.randomUUID().toString(),
        name = "Zapatillas Urbanas",
        price = 89990.0,
        rating = 4.5f,
        description = "Zapatillas cómodas y con estilo para el día a día. Material resistente y suela antideslizante.",
        imageUrl = "https://assets.adidas.com/images/w_383,h_383,f_auto,q_auto,fl_lossy,c_fill,g_auto/d8e541f3a1ca488c9057c75100f4f71d_9366/zapatillas-streettalk.jpg",
        sellerId = "tienda_oficial"
    ),
    Producto(
        id = "2",
        name = "Chaqueta Cortaviento",
        price = 54.90,
        description = "Ideal para días de viento.",
        // ✅ AQUÍ ESTÁ TU URL ACTUALIZADA
        imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/08/27/fa/1724728959fb77d71664687db69f1bda1616c20015_thumbnail_560x.webp",
        rating = 4.2f,
        sellerId = "admin"
    ),
    Producto(
        id = "3",
        name = "Mochila Urbana",
        price = 45.00,
        description = "Mochila resistente con compartimento para laptop.",
        imageUrl = "https://media.falabella.com/falabellaCL/139942163_01/w=800,h=800,fit=pad",
        rating = 4.8f,
        sellerId = "admin"
    )
)
