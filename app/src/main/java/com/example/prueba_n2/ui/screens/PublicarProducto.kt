package com.example.prueba_n2.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.prueba_n2.model.Producto
// ✅ 1. IMPORTAR LA CLASE USUARIO
import com.example.prueba_n2.model.Usuario
import java.io.File
import java.util.Objects
import java.util.UUID
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicarProducto(
    // ✅ 2. AÑADIR EL PARÁMETRO currentUser
    currentUser: Usuario?,
    onProductoPublicado: (Producto) -> Unit,
    onBack: () -> Unit
) {
    var nombreArticulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // --- LAUNCHERS (Sin cambios) ---

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            selectedImageUri = photoUri
        } else {
            Toast.makeText(context, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val newUri = createImageUri(context)
            photoUri = newUri
            cameraLauncher.launch(newUri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    // --- UI (Scaffold y Column - Sin cambios en la estructura) ---

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicar Artículo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("<-") // Reemplaza con un Icon si lo prefieres
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nombreArticulo,
                onValueChange = { nombreArticulo = it },
                label = { Text("Nombre del Artículo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { newValue ->
                    if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        precio = newValue
                    }
                },
                label = { Text("Precio (ej. 49.99)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Visor de Imagen (Sin cambios)
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(selectedImageUri)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("No hay imagen seleccionada")
                    }
                }
            }

            // Botones de Galería y Cámara (Sin cambios)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text("Galería")
                }

                Button(onClick = {
                    val permission = Manifest.permission.CAMERA
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        val newUri = createImageUri(context)
                        photoUri = newUri
                        cameraLauncher.launch(newUri)
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }) {
                    Text("Usar Cámara")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Publicar
            Button(
                onClick = {
                    val nuevoProducto = Producto(
                        id = UUID.randomUUID().toString(),
                        name = nombreArticulo,
                        price = precio.toDoubleOrNull() ?: 0.0,
                        description = descripcion,
                        imageUrl = selectedImageUri?.toString() ?: "",
                        rating = 0.0f, // Rating inicial
                        // ✅ 3. USA EL ID DEL USUARIO ACTUAL
                        // Si currentUser es nulo, se usa un ID de error para evitar que la app crashee,
                        // aunque el botón estará deshabilitado.
                        sellerId = (currentUser?.id ?: "error_user_id").toString()
                    )
                    onProductoPublicado(nuevoProducto)
                },
                modifier = Modifier.fillMaxWidth(),
                // ✅ 4. ASEGÚRATE DE QUE EL USUARIO NO SEA NULO PARA HABILITAR EL BOTÓN
                enabled = nombreArticulo.isNotBlank()
                        && precio.isNotBlank()
                        && descripcion.isNotBlank()
                        && selectedImageUri != null
                        && currentUser != null // <-- Comprobación añadida
            ) {
                Text("Publicar Artículo")
            }
        }
    }
}

// --- FUNCIÓN AUXILIAR (Sin cambios) ---
private fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".fileprovider",
        file
    )
}

