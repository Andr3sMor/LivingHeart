package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.runtime.getValue
import android.util.Log

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.isEmpty
import androidx.core.graphics.set
import androidx.lifecycle.ViewModel
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await // Importante para usar .await()
import kotlin.io.path.exists

// No se necesita Enum para navegación interna de esta pantalla si es una sola vista.

class PerfilUsuarioViewModel : ViewModel() {

    // --- Estado del Perfil del Usuario ---
    var nombre by mutableStateOf("")
    var email by mutableStateOf("") // El email usualmente viene de FirebaseAuth, pero puede guardarse en Firestore también
    var notificationsEnabled by mutableStateOf(true)

    // Otras propiedades que podrían ser parte del perfil y cargarse/guardarse
    var apellido by mutableStateOf("")
    var peso by mutableStateOf("")
    var estatura by mutableStateOf("")
    var edad by mutableStateOf("")
    var actividadFisica by mutableStateOf("")
    var dropdownExpanded by mutableStateOf(false)
    val listaActividades = listOf("Sedentario", "Ligera", "Moderada", "Intensa")


    // --- Estado de Métricas Recientes ---
    var lastBpm by mutableStateOf<Int?>(null)
    var trainingZone by mutableStateOf("")

    // --- Estado para Modales ---
    var showDeleteConfirmationDialog by mutableStateOf(false)

    // Referencias a Firebase (pueden ser inicializadas aquí o pasadas por inyección de dependencias)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // --- Funciones de Lógica de Negocio (CRUD Usuario y Métricas) ---

    fun loadUserProfile() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                // El email se puede obtener directamente de currentUser
                email = currentUser.email ?: ""

                try {
                    val userDocumentRef = firestore.collection("users").document(userId)
                    val documentSnapshot = userDocumentRef.get().await() // .await() suspende la corrutina hasta que se completa

                    if (documentSnapshot.exists()) {
                        nombre = documentSnapshot.getString("nombre") ?: ""
                        // Si guardas el email en Firestore y quieres priorizarlo, descomenta:
                        // email = documentSnapshot.getString("email") ?: currentUser.email ?: ""
                        notificationsEnabled = documentSnapshot.getBoolean("notificationsEnabled") ?: true
                        apellido = documentSnapshot.getString("apellido") ?: ""
                        peso = documentSnapshot.getString("peso") ?: ""
                        estatura = documentSnapshot.getString("estatura") ?: ""
                        edad = documentSnapshot.getString("edad") ?: ""
                        actividadFisica = documentSnapshot.getString("actividadFisica") ?: ""

                        Log.d("PerfilVM", "Datos del usuario cargados: $nombre, $email")
                    } else {
                        // El documento no existe, podría ser un usuario nuevo o un error.
                        // Puedes decidir crear un documento por defecto aquí si es necesario.
                        Log.w("PerfilVM", "El documento del usuario no existe para UID: $userId. Se usarán valores por defecto.")
                        // Si es un usuario nuevo y el documento no existe, el email ya está seteado desde auth.
                        // El nombre podría quedar vacío o podrías intentar obtenerlo de currentUser.displayName
                        nombre = currentUser.displayName ?: ""
                        // Las demás propiedades mantendrán sus valores por defecto (mutableStateOf iniciales)
                    }
                } catch (e: Exception) {
                    Log.e("PerfilVM", "Error al cargar el perfil del usuario desde Firestore", e)
                    // Manejar el error, por ejemplo, mostrando un mensaje al usuario a través de un StateFlow
                    // o manteniendo los valores por defecto.
                }
            } else {
                Log.w("PerfilVM", "No hay usuario autenticado para cargar el perfil.")
                // Manejar el caso de que no haya usuario (ej. navegar a login)
                // Por ahora, se mantendrán los valores por defecto de las propiedades.
            }
        }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                val userData = hashMapOf(
                    "nombre" to nombre,
                    "email" to email, // Puedes decidir si el email es editable y se guarda aquí o solo se lee de Auth
                    "notificationsEnabled" to notificationsEnabled,
                    "apellido" to apellido,
                    "peso" to peso,
                    "estatura" to estatura,
                    "edad" to edad,
                    "actividadFisica" to actividadFisica
                    // Asegúrate de que los nombres de campo ("nombre", "email", etc.)
                    // coincidan exactamente con los que usas en Firestore.
                )

                try {
                    firestore.collection("users").document(userId)
                        .set(userData, SetOptions.merge()) // SetOptions.merge() actualiza solo los campos proporcionados
                        .await()
                    Log.d("PerfilVM", "Perfil de usuario guardado exitosamente.")
                    // TODO: Podrías añadir un callback o un StateFlow para notificar éxito/fracaso a la UI
                } catch (e: Exception) {
                    Log.e("PerfilVM", "Error al guardar el perfil del usuario en Firestore", e)
                    // TODO: Manejar el error (mostrar mensaje al usuario)
                }
            } else {
                Log.w("PerfilVM", "No hay usuario autenticado para guardar el perfil.")
                // TODO: Manejar este caso (poco probable si el usuario está en esta pantalla)
            }
        }
    }

    fun deleteUserAccount(onAccountDeleted: () -> Unit) {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                showDeleteConfirmationDialog = false // Cerrar el diálogo primero
                try {
                    // 1. Eliminar datos de Firestore (u otros servicios asociados)
                    firestore.collection("users").document(userId).delete().await()
                    Log.d("PerfilVM", "Datos de Firestore eliminados para el usuario $userId")

                    // 2. Eliminar cuenta de Firebase Authentication
                    currentUser.delete().await()
                    Log.d("PerfilVM", "Cuenta de Firebase Auth eliminada para el usuario $userId")

                    onAccountDeleted() // Llama al callback para navegar fuera
                } catch (e: Exception) {
                    Log.e("PerfilVM", "Error al eliminar la cuenta del usuario", e)
                    // TODO: Manejar el error (mostrar mensaje al usuario, considerar re-autenticación si es necesario)
                    // Si algo falla, asegúrate de no llamar onAccountDeleted si la eliminación no fue completa,
                    // o manejar el error de forma que el usuario entienda qué pasó.
                }
            } else {
                Log.w("PerfilVM", "No hay usuario autenticado para eliminar la cuenta.")
            }
        }
    }

    fun listenToRecentMetrics() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                // Ejemplo de escucha en tiempo real. Ajusta "metrics_collection" y los nombres de los campos.
                // Esta es una implementación básica. Podrías querer limitar a 1 y ordenar por timestamp.
                val metricsCollectionRef = firestore.collection("users").document(userId).collection("metrics")

                // Para obtener solo la más reciente, ordena y limita:
                metricsCollectionRef
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING) // Asume que tienes un campo "timestamp"
                    .limit(1)
                    .addSnapshotListener { snapshots, e ->
                        if (e != null) {
                            Log.w("PerfilVM", "Error al escuchar métricas", e)
                            return@addSnapshotListener
                        }

                        if (snapshots != null && !snapshots.isEmpty) {
                            val document = snapshots.documents.first() // Solo el primer documento (el más reciente)
                            lastBpm = document.getLong("bpm")?.toInt() // Asume que tienes un campo "bpm" de tipo Long
                            trainingZone = document.getString("trainingZone") ?: "" // Asume que tienes un campo "trainingZone"
                            Log.d("PerfilVM", "Métricas actualizadas: BPM - $lastBpm, Zona - $trainingZone")
                        } else {
                            Log.d("PerfilVM", "No hay documentos de métricas o la colección está vacía.")
                            lastBpm = null
                            trainingZone = ""
                        }
                    }
            } else {
                Log.w("PerfilVM", "No hay usuario autenticado para escuchar métricas.")
            }
        }
    }
}