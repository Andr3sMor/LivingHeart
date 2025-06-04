package com.cardiovascularmodel.livingheart.Ui.Dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.util.remove
import androidx.core.util.set
import androidx.lifecycle.ViewModel
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.io.path.exists
import kotlin.random.Random

// Data class para representar los datos del perfil del usuario en Firestore
data class UserProfile(
    val userName: String = "",
    val userEmail: String = "",
    val notificationsEnabled: Boolean = true,
    val activityReminderNotifications: Boolean = true,
    val achievementNotifications: Boolean = true,
    val appUpdateNotifications: Boolean = false
    // Puedes añadir más campos aquí si es necesario
)

class DashboardViewModel : ViewModel() {

    // Instancias de Firebase
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = auth.currentUser

    // --- Propiedades del Dashboard (datos de salud simulados) ---
    var steps by mutableStateOf(6000)
        private set
    var stepsGoal by mutableStateOf(6000)
        private set
    // ... (otras propiedades de salud como calories, heartRate, etc. se mantienen igual)
    var heartRate by mutableStateOf(72)
        private set
    var hrv by mutableStateOf(55)
        private set
    var spo2 by mutableStateOf(97)
        private set
    // ... (resto de tus propiedades de salud)
    var calories by mutableStateOf(251)
        private set
    var caloriesGoal by mutableStateOf(301)
        private set
    var workoutMinutes by mutableStateOf(15)
        private set
    var workoutGoal by mutableStateOf(31)
        private set

    // --- Propiedades del Perfil del Usuario (conectadas a Firebase) ---
    var userName by mutableStateOf("Cargando...")
        private set
    var userEmail by mutableStateOf("Cargando...")
        private set
    var notificationsEnabled by mutableStateOf(true)
        private set
    var activityReminderNotifications by mutableStateOf(true)
        private set
    var achievementNotifications by mutableStateOf(true)
        private set
    var appUpdateNotifications by mutableStateOf(false)
        private set

    // Indica si los datos del perfil se están cargando
    var isLoadingProfile by mutableStateOf(true)
        private set

    private var simulationJob: Job? = null
    private var userProfileListener: com.google.firebase.firestore.ListenerRegistration? = null


    init {
        startHealthDataSimulation()
        if (currentUser != null) {
            loadUserProfile() // Cargar perfil al iniciar si el usuario está logueado
            listenToUserProfileChanges() // Escuchar cambios en tiempo real
        } else {
            // Manejar el caso donde no hay usuario logueado
            // Podrías redirigir a la pantalla de login o mostrar un estado de "invitado"
            userName = "Invitado"
            userEmail = ""
            isLoadingProfile = false
        }
    }

    private fun startHealthDataSimulation() {
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            while (isActive) {
                heartRate = Random.nextInt(60, 101)
                hrv = Random.nextInt(40, 71)
                spo2 = Random.nextInt(95, 100)
                delay(2000)
            }
        }
    }

    // --- Funciones para interactuar con Firestore ---

    private fun getUserDocumentRef() = currentUser?.uid?.let { userId ->
        firestore.collection("users").document(userId)
    }

    private fun loadUserProfile() {
        isLoadingProfile = true
        viewModelScope.launch {
            try {
                val documentRef = getUserDocumentRef()
                if (documentRef != null) {
                    val snapshot = documentRef.get().await()
                    if (snapshot.exists()) {
                        val userProfile = snapshot.toObject(UserProfile::class.java)
                        userProfile?.let {
                            userName = it.userName
                            userEmail = it.userEmail
                            notificationsEnabled = it.notificationsEnabled
                            activityReminderNotifications = it.activityReminderNotifications
                            achievementNotifications = it.achievementNotifications
                            appUpdateNotifications = it.appUpdateNotifications
                        }
                    } else {
                        // El documento no existe, podrías crear uno con valores por defecto
                        // o usar los valores iniciales que ya tienes.
                        // Por ahora, simplemente usamos los valores por defecto si no hay perfil.
                        // Si el email está disponible desde auth, lo usamos.
                        userName = currentUser?.displayName ?: "$userName"
                        userEmail = currentUser?.email ?: "sin_email@example.com"
                        // Guardar un perfil inicial si no existe
                        saveUserProfileData()
                    }
                }
            } catch (e: Exception) {
                // Manejar errores (e.g., mostrar un mensaje al usuario)
                userName = "Error al cargar"
                userEmail = "Error"
                // Log.e("DashboardViewModel", "Error loading user profile", e)
            } finally {
                isLoadingProfile = false
            }
        }
    }

    private fun listenToUserProfileChanges() {
        val documentRef = getUserDocumentRef()
        if (documentRef != null) {
            userProfileListener = documentRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Log.w("DashboardViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val userProfile = snapshot.toObject(UserProfile::class.java)
                    userProfile?.let {
                        userName = it.userName
                        userEmail = it.userEmail
                        notificationsEnabled = it.notificationsEnabled
                        activityReminderNotifications = it.activityReminderNotifications
                        achievementNotifications = it.achievementNotifications
                        appUpdateNotifications = it.appUpdateNotifications
                        isLoadingProfile = false // Asegurarse de que el loading se desactiva
                    }
                } else {
                    // Log.d("DashboardViewModel", "Current data: null")
                    // Podrías manejar el caso de que el documento se elimine
                }
            }
        }
    }


    private fun saveUserProfileData() {
        if (currentUser == null) return // No guardar si no hay usuario

        val userProfile = UserProfile(
            userName = userName,
            userEmail = userEmail,
            notificationsEnabled = notificationsEnabled,
            activityReminderNotifications = activityReminderNotifications,
            achievementNotifications = achievementNotifications,
            appUpdateNotifications = appUpdateNotifications
        )

        viewModelScope.launch {
            try {
                getUserDocumentRef()?.set(userProfile)?.await()
                // Log.d("DashboardViewModel", "User profile successfully written!")
            } catch (e: Exception) {
                // Log.w("DashboardViewModel", "Error writing user profile", e)
                // Manejar error al guardar (e.g., mostrar un Snackbar)
            }
        }
    }

    // --- Funciones para actualizar el estado (ejemplos) ---
    // Ahora, al actualizar, también guardamos en Firebase

    fun updateUserName(newName: String) {
        userName = newName
        saveUserProfileData()
    }

    fun updateUserEmail(newEmail: String) {
        // Generalmente, el email se maneja a través de Firebase Auth
        // y se actualiza allí primero. Aquí lo actualizamos localmente y en el perfil
        // si se gestiona de forma independiente al email de autenticación.
        // Considera si este campo debe ser editable directamente o si se sincroniza desde Auth.
        userEmail = newEmail
        saveUserProfileData()
    }

    fun toggleNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
        if (!enabled) {
            activityReminderNotifications = false
            achievementNotifications = false
            appUpdateNotifications = false
        }
        saveUserProfileData()
    }

    fun toggleActivityReminderNotifications(enabled: Boolean) {
        activityReminderNotifications = enabled
        saveUserProfileData()
    }

    fun toggleAchievementNotifications(enabled: Boolean) {
        achievementNotifications = enabled
        saveUserProfileData()
    }

    fun toggleAppUpdateNotifications(enabled: Boolean) {
        appUpdateNotifications = enabled
        saveUserProfileData()
    }

    override fun onCleared() {
        super.onCleared()
        simulationJob?.cancel()
        userProfileListener?.remove() // Importante: remover el listener para evitar memory leaks
    }
}