package com.cardiovascularmodel.livingheart.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Modelo de datos para el perfil del usuario
data class UserProfileData(
    val userId: String = "",                // ID único del usuario (ej. de Firebase Auth)
    val name: String = "",
    val email: String = "",
    val registrationDate: String = "",      // Podría ser un tipo Date/Timestamp
    val profileImageUrl: String? = null,    // URL de la imagen de perfil
    val notificationsEnabled: Boolean = true
    // Puedes añadir más campos según necesites:
    // val phoneNumber: String? = null,
    // val dateOfBirth: String? = null,
)

// Estados para la UI, especialmente para operaciones asíncronas
sealed interface UserProfileUiState {
    object Loading : UserProfileUiState
    data class Success(val userProfile: UserProfileData) : UserProfileUiState
    data class Error(val message: String) : UserProfileUiState
}

class UserViewModel : ViewModel() {

    // Estado de la UI para el perfil del usuario
    var userProfileState by mutableStateOf<UserProfileUiState>(UserProfileUiState.Loading)
        private set

    // Variables para manejar la edición en la UI antes de guardar
    // Estos podrían estar en la pantalla y pasarse al ViewModel, o manejarse aquí
    // si la edición es más compleja o requiere validación en el ViewModel.
    // Por simplicidad, asumiremos que la pantalla maneja los campos de texto temporales.

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        userProfileState = UserProfileUiState.Loading
        viewModelScope.launch {
            try {
                // --- SIMULACIÓN DE CARGA DESDE FIREBASE ---
                delay(1500) // Simular retraso de red

                // En una implementación real, aquí obtendrías el ID del usuario actual de Firebase Auth
                val currentUserId = "simulated_user_id_123" // Ejemplo
                // Y luego harías una llamada a Firestore para obtener el documento del usuario
                // val firestoreDocument = Firebase.firestore.collection("users").document(currentUserId).get().await()
                // val user = firestoreDocument.toObject(UserProfileData::class.java)

                if (currentUserId.isNotEmpty()) { // Simular que se encontró el usuario
                    val fetchedUserProfile = UserProfileData(
                        userId = currentUserId,
                        name = "Usuario Ejemplo",
                        email = "usuario@ejemplo.com",
                        registrationDate = "15/03/2023",
                        profileImageUrl = null, // "https://example.com/profile.jpg"
                        notificationsEnabled = true
                    )
                    userProfileState = UserProfileUiState.Success(fetchedUserProfile)
                } else {
                    userProfileState =
                        UserProfileUiState.Error("No se pudo encontrar el perfil del usuario.")
                }
                // --- FIN DE SIMULACIÓN ---

            } catch (e: Exception) {
                // Manejo de errores (ej. problemas de red, usuario no encontrado)
                userProfileState =
                    UserProfileUiState.Error("Error al cargar el perfil: ${e.message}")
                // Podrías registrar el error: Log.e("UserViewModel", "Error loading profile", e)
            }
        }
    }

    fun updateUserName(newName: String) {
        val currentState = userProfileState
        if (currentState is UserProfileUiState.Success) {
            // Actualizar UI inmediatamente para feedback rápido (opcional, pero buena UX)
            val updatedProfileForUi = currentState.userProfile.copy(name = newName)
            userProfileState = UserProfileUiState.Success(updatedProfileForUi)

            viewModelScope.launch {
                try {
                    // --- SIMULACIÓN DE ACTUALIZACIÓN EN FIREBASE ---
                    delay(1000) // Simular retraso de red
                    // Aquí actualizarías el campo 'name' en el documento de Firestore
                    // Firebase.firestore.collection("users").document(currentState.userProfile.userId)
                    //    .update("name", newName)
                    //    .await()
                    println("Nombre actualizado en 'Firebase' a: $newName")
                    // Si la operación en backend fue exitosa, el estado de la UI ya está actualizado.
                    // Si fallara, podrías revertir el cambio en la UI o mostrar un error persistente.
                } catch (e: Exception) {
                    // Revertir el cambio en la UI si la actualización del backend falla
                    userProfileState =
                        UserProfileUiState.Success(currentState.userProfile) // Revertir al estado original
                    // Mostrar un mensaje de error (podrías usar un StateFlow/SharedFlow para eventos de Snackbar)
                    println("Error al actualizar el nombre: ${e.message}")
                    // userProfileState = UserProfileUiState.Error("Error al actualizar el nombre.") // Alternativa
                }
            }
        }
    }

    fun updateUserEmail(newEmail: String) {
        val currentState = userProfileState
        if (currentState is UserProfileUiState.Success) {
            // Actualizar UI inmediatamente
            val updatedProfileForUi = currentState.userProfile.copy(email = newEmail)
            userProfileState = UserProfileUiState.Success(updatedProfileForUi)

            viewModelScope.launch {
                try {
                    // --- SIMULACIÓN DE ACTUALIZACIÓN EN FIREBASE AUTH Y FIRESTORE ---
                    delay(1000)
                    // 1. Actualizar email en Firebase Auth (requiere reautenticación a veces)
                    // Firebase.auth.currentUser?.updateEmail(newEmail)?.await()
                    // 2. Actualizar email en Firestore
                    // Firebase.firestore.collection("users").document(currentState.userProfile.userId)
                    //    .update("email", newEmail)
                    //    .await()
                    println("Email actualizado en 'Firebase' a: $newEmail")
                } catch (e: Exception) {
                    userProfileState =
                        UserProfileUiState.Success(currentState.userProfile) // Revertir
                    println("Error al actualizar el email: ${e.message}")
                }
            }
        }
    }

    fun toggleNotificationSettings(isEnabled: Boolean) {
        val currentState = userProfileState
        if (currentState is UserProfileUiState.Success) {
            val updatedProfileForUi =
                currentState.userProfile.copy(notificationsEnabled = isEnabled)
            userProfileState = UserProfileUiState.Success(updatedProfileForUi)

            viewModelScope.launch {
                try {
                    // --- SIMULACIÓN DE ACTUALIZACIÓN EN FIREBASE ---
                    delay(500)
                    // Firebase.firestore.collection("users").document(currentState.userProfile.userId)
                    //    .update("notificationsEnabled", isEnabled)
                    //    .await()
                    println("Configuración de notificaciones actualizada en 'Firebase' a: $isEnabled")
                } catch (e: Exception) {
                    userProfileState =
                        UserProfileUiState.Success(currentState.userProfile) // Revertir
                    println("Error al actualizar notificaciones: ${e.message}")
                }
            }
        }
    }

    // Podrías añadir una función para actualizar la imagen de perfil
    // fun updateUserProfileImage(imageUrl: String) { ... }

    fun requestAccountDeletion(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = userProfileState
        if (currentState is UserProfileUiState.Success) {
            viewModelScope.launch {
                try {
                    // --- SIMULACIÓN DE ELIMINACIÓN DE CUENTA EN FIREBASE ---
                    // 1. Eliminar datos de Firestore (u otros servicios como Storage)
                    // Firebase.firestore.collection("users").document(currentState.userProfile.userId).delete().await()
                    // 2. Eliminar cuenta de Firebase Auth (esta es la acción final)
                    // Firebase.auth.currentUser?.delete()?.await()
                    delay(2000) // Simular operación
                    println("Cuenta eliminada de 'Firebase' para el usuario: ${currentState.userProfile.userId}")

                    // Limpiar estado local y notificar éxito
                    userProfileState = UserProfileUiState.Loading // O un estado "SignedOut"
                    onSuccess()

                } catch (e: Exception) {
                    // Manejar errores (ej. requiere reautenticación reciente para eliminar)
                    println("Error al eliminar la cuenta: ${e.message}")
                    onError("Error al eliminar la cuenta: ${e.message}")
                }
            }
        } else {
            onError("No hay información de usuario para eliminar la cuenta.")
        }
    }

    // Si necesitas refrescar los datos manualmente
    fun refreshUserProfile() {
        loadUserProfile()
    }
}