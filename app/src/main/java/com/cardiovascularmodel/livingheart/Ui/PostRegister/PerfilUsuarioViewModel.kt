package com.cardiovascularmodel.livingheart.Ui.PostRegister // Asegúrate que el package sea el correcto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// No se necesita Enum para navegación interna de esta pantalla si es una sola vista.

class PerfilUsuarioViewModel : ViewModel() {

    // --- Estado del Perfil del Usuario ---
    var nombre by mutableStateOf("")
    var email by mutableStateOf("")
    var notificationsEnabled by mutableStateOf(true)

    // Otras propiedades que podrían ser parte del perfil y cargarse/guardarse
    var apellido by mutableStateOf("")
    var peso by mutableStateOf("")
    var estatura by mutableStateOf("")
    var edad by mutableStateOf("")
    var actividadFisica by mutableStateOf("") // Considera si esto se edita aquí o en otro lado
    var dropdownExpanded by mutableStateOf(false)
    val listaActividades = listOf("Sedentario", "Ligera", "Moderada", "Intensa")


    // --- Estado de Métricas Recientes ---
    var lastBpm by mutableStateOf<Int?>(null)
    var trainingZone by mutableStateOf("")

    // --- Estado para Modales ---
    var showDeleteConfirmationDialog by mutableStateOf(false)

    // --- Funciones de Lógica de Negocio (CRUD Usuario y Métricas) ---

    fun loadUserProfile() {
        viewModelScope.launch {
            // TODO: Implementar la carga de datos del usuario (nombre, email, notificationsEnabled, etc.) desde Firestore.
            // Ejemplo:
            // val userId = FirebaseAuth.getInstance().currentUser?.uid
            // if (userId != null) {
            //     val userDocument = FirebaseFirestore.getInstance().collection("users").document(userId).get().await()
            //     nombre = userDocument.getString("nombre") ?: ""
            //     email = userDocument.getString("email") ?: ""
            //     notificationsEnabled = userDocument.getBoolean("notificationsEnabled") ?: true
            //     // Cargar otras propiedades...
            // }
        }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            // TODO: Implementar el guardado de datos (nombre, email, notificationsEnabled, etc.) en Firestore.
            // Ejemplo:
            // val userId = FirebaseAuth.getInstance().currentUser?.uid
            // if (userId != null) {
            //     val userData = mapOf(
            //         "nombre" to nombre,
            //         "email" to email,
            //         "notificationsEnabled" to notificationsEnabled
            //         // Guardar otras propiedades...
            //     )
            //     FirebaseFirestore.getInstance().collection("users").document(userId).set(userData, SetOptions.merge()).await()
            //      // Podrías añadir un callback para notificar éxito/fracaso a la UI
            // }
        }
    }

    fun deleteUserAccount(onAccountDeleted: () -> Unit) {
        viewModelScope.launch {
            // TODO: Implementar la eliminación de la cuenta y todos los datos del usuario en Firestore.
            // TODO: Manejar la reautenticación si es necesario.
            // TODO: Cerrar sesión después de eliminar la cuenta.
            showDeleteConfirmationDialog = false // Cerrar el diálogo primero
            // Ejemplo:
            // val userId = FirebaseAuth.getInstance().currentUser?.uid
            // if (userId != null) {
            //     FirebaseFirestore.getInstance().collection("users").document(userId).delete().await()
            //     FirebaseAuth.getInstance().currentUser?.delete()?.await() // Esto cierra sesión y elimina
            //     onAccountDeleted() // Llama al callback para navegar fuera
            // }
            // Si algo falla, asegúrate de no llamar onAccountDeleted o manejar el error
        }
    }

    fun listenToRecentMetrics() {
        viewModelScope.launch {
            // TODO: Implementar la escucha en tiempo real de las métricas desde Firebase.
            // Ejemplo:
            // val userId = FirebaseAuth.getInstance().currentUser?.uid
            // if (userId != null) {
            //    FirebaseFirestore.getInstance().collection("users").document(userId).collection("metrics")
            //        .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            //        .addSnapshotListener { snapshots, e ->
            //            if (e != null) { /* Manejar error */ return@addSnapshotListener }
            //            snapshots?.documents?.firstOrNull()?.let {
            //                lastBpm = it.getLong("bpm")?.toInt()
            //                trainingZone = it.getString("trainingZone") ?: ""
            //            }
            //        }
            // }
        }
    }
}