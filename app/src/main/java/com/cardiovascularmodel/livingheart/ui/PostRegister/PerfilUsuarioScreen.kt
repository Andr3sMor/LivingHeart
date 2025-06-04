package com.cardiovascularmodel.livingheart.Ui.PostRegister // Asegúrate que el package sea el correcto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // Para el botón de "Atrás" en la TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    appViewModel: PerfilUsuarioViewModel = viewModel(),
    onNavigateBack: () -> Unit, // Para volver si se accede desde un NavHost
    onAccountDeleted: () -> Unit // Para navegar fuera después de eliminar la cuenta
) {
    // Cargar perfil del usuario y métricas al iniciar la pantalla
    LaunchedEffect(Unit) {
        appViewModel.loadUserProfile()
        appViewModel.listenToRecentMetrics()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario") },
                navigationIcon = {
                    // Si esta pantalla es parte de una navegación mayor,
                    // podrías tener un botón de "Atrás" en lugar de un icono de menú.
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
                // No actions needed here if "Cerrar Sesión" is in the main app's side menu
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Scroll para todo el contenido de la pantalla
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // --- Sección de Datos Personales ---
                Text("Datos Personales", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = appViewModel.nombre,
                    onValueChange = { appViewModel.nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = appViewModel.email,
                    onValueChange = { appViewModel.email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // Aquí podrías añadir más campos del perfil si es necesario (apellido, peso, etc.)
                // Ejemplo:
                // OutlinedTextField(
                //     value = appViewModel.apellido,
                //     onValueChange = { appViewModel.apellido = it },
                //     label = { Text("Apellido") },
                //     modifier = Modifier.fillMaxWidth()
                // )

                Spacer(modifier = Modifier.height(16.dp))
                Text("Configuración", style = MaterialTheme.typography.titleMedium)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Habilitar notificaciones")
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = appViewModel.notificationsEnabled,
                        onCheckedChange = { appViewModel.notificationsEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp)) // Más espacio antes de los botones de acción

                Button(
                    onClick = { appViewModel.saveUserProfile() /* TODO: Añadir feedback al usuario */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { appViewModel.showDeleteConfirmationDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar Cuenta")
                }

                Spacer(modifier = Modifier.height(24.dp)) // Espacio antes del widget de métricas

                // --- Widget de Métricas Recientes ---
                RecentMetricsWidget(
                    lastBpm = appViewModel.lastBpm,
                    trainingZone = appViewModel.trainingZone
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio al final del scroll
            }

            // --- Diálogo de Confirmación para Eliminar Cuenta ---
            if (appViewModel.showDeleteConfirmationDialog) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        appViewModel.deleteUserAccount(onAccountDeleted = onAccountDeleted)
                    },
                    onDismiss = { appViewModel.showDeleteConfirmationDialog = false }
                )
            }
        }
    }
}

// El widget de métricas puede seguir siendo un Composable separado dentro del mismo archivo
// o simplemente parte de la Column principal si es sencillo.
@Composable
fun RecentMetricsWidget(lastBpm: Int?, trainingZone: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centrar texto si quieres
        ) {
            Text("Métricas Recientes", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("Último BPM: ${lastBpm ?: "N/A"}")
            Text("Zona de Entrenamiento: ${trainingZone.ifEmpty { "N/A" }}")
        }
    }
}

// El diálogo de confirmación también puede estar aquí
@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar Eliminación") },
        text = { Text("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer y todos tus datos serán borrados.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}