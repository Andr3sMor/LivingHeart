package com.cardiovascularmodel.livingheart.Ui.Dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random



class DashboardViewModel : ViewModel() {

    var steps by mutableStateOf(6000)
        private set

    var stepsGoal by mutableStateOf(11000)
        private set

    var calories by mutableStateOf(251)
        private set

    var caloriesGoal by mutableStateOf(301)
        private set

    var workoutMinutes by mutableStateOf(15)
        private set

    var workoutGoal by mutableStateOf(31)
        private set

    // Esta es la propiedad clave que actualizaremos con datos de Google Fit
    var heartRate by mutableStateOf<Int?>(null) // Inicializa como null o un valor de carga
        private set

    var hrv by mutableStateOf(55)
        private set

    var spo2 by mutableStateOf(97)
        private set

    // Nuevas propiedades para el perfil
    var userName by mutableStateOf("Prueba") // Deberías cargar esto desde tus datos
        private set

    var userEmail by mutableStateOf("usuario@example.com") // Deberías cargar esto desde tus datos
        private set

    var notificationsEnabled by mutableStateOf(true)
        private set

    // Opcional: Configuraciones de notificación más granulares
    var activityReminderNotifications by mutableStateOf(true)
        private set
    var achievementNotifications by mutableStateOf(true)
        private set
    var appUpdateNotifications by mutableStateOf(false)
        private set

    // Job para controlar la coroutine de simulación
    private var simulationJob: Job? = null

    init {
        startHealthDataSimulation()
    }

    private fun startHealthDataSimulation() {
        // Cancela cualquier simulación anterior para evitar múltiples coroutines
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            while (isActive) { // Se ejecuta mientras el ViewModel esté activo
                // Simula la variación del ritmo cardíaco (ej. entre 60 y 100)

                // Simula la variación de HRV (ej. entre 40 y 70)
                hrv = Random.nextInt(40, 71)

                // Simula la variación de SpO2 (ej. entre 95 y 99)
                spo2 = Random.nextInt(95, 100)

                delay(2000) // Espera 2 segundos antes de la próxima actualización
            }
        }
    }

    // Funciones para actualizar el estado (ejemplos)
    fun updateUserName(newName: String) {
        userName = newName
    }
    fun updateHeartRateFromGoogleFit(newHeartRate: Int?) {
        heartRate = newHeartRate
        // Podrías añadir lógica adicional aquí si es necesario cuando se actualiza el heartRate
    }

    fun updateUserEmail(newEmail: String) {
        userEmail = newEmail
    }

    fun toggleNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
        // Si se deshabilitan todas, podrías deshabilitar también las granulares
        if (!enabled) {
            activityReminderNotifications = false
            achievementNotifications = false
            appUpdateNotifications = false
        }
    }

    fun toggleActivityReminderNotifications(enabled: Boolean) {
        activityReminderNotifications = enabled
    }

    fun toggleAchievementNotifications(enabled: Boolean) {
        achievementNotifications = enabled
    }

    fun toggleAppUpdateNotifications(enabled: Boolean) {
        appUpdateNotifications = enabled
    }

    // Es importante cancelar la coroutine cuando el ViewModel se destruye
    override fun onCleared() {
        super.onCleared()
        simulationJob?.cancel()
    }
}