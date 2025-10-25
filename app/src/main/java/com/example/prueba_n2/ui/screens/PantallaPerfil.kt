package com.example.prueba_n2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource // Para usar R.string.app_name
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.prueba_n2.R // Importa tu R
import com.example.prueba_n2.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(
    usuario: Usuario?,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Cerrar Sesión")
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (usuario != null) {
                Text("Nombre:", fontWeight = FontWeight.Bold)
                Text(usuario.nombre)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Email:", fontWeight = FontWeight.Bold)
                Text(usuario.email)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Contraseña:", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = usuario.contrasena,
                    onValueChange = {},
                    readOnly = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cerrar Sesión")
                }
            } else {
                Text("No se pudo cargar la información del perfil.")
            }
        }
    }
}