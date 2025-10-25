package com.example.prueba_n2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.prueba_n2.ui.login.LoginState // Import LoginState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIngreso(
    // ✅ ADDED this parameter:
    loginState: LoginState,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit // Keep this to navigate on success
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Show messages based on loginState
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                Toast.makeText(context, "¡Ingreso exitoso!", Toast.LENGTH_SHORT).show()
                onLoginSuccess() // Trigger navigation
            }
            is LoginState.Error -> {
                Toast.makeText(context, loginState.message, Toast.LENGTH_LONG).show()
            }
            LoginState.Empty -> {
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Urban Shop", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            // Disable button while loading if you add a Loading state
            // enabled = loginState !is LoginState.Loading,
            onClick = { onLogin(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}