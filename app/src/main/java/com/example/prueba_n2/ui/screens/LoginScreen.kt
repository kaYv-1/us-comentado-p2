package com.example.prueba_n2.ui.screens

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba_n2.model.AppDatabase
import com.example.prueba_n2.repository.UsuarioRepository
import com.example.prueba_n2.viewmodel.LoginViewModel
import com.example.prueba_n2.viewmodel.LoginViewModelFactory
import com.example.prueba_n2.viewmodel.LoginState

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val factory = remember(context, scope) {
        val db = AppDatabase.getDatabase(context.applicationContext, scope)
        val repository = UsuarioRepository(db.usuarioDao())
        LoginViewModelFactory(repository)
    }

    val viewModel: LoginViewModel = viewModel(factory = factory)


    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isRegisterMode) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isRegisterMode) {
            Button(
                onClick = { viewModel.registrarUsuario(nombre, email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        } else {
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = loginState) {
            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }
            is LoginState.Error -> {
                Text(state.message, color = MaterialTheme.colorScheme.error)
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        val switchText = if (isRegisterMode) {
            "¿Ya tienes cuenta? Inicia sesión"
        } else {
            "¿No tienes cuenta? Regístrate"
        }
        Text(
            text = switchText,
            modifier = Modifier.clickable { isRegisterMode = !isRegisterMode },
            color = MaterialTheme.colorScheme.primary
        )
    }
}
