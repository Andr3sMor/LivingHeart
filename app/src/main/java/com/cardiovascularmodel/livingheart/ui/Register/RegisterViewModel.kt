// app/src/main/java/com/cardiovascularmodel/livingheart/Ui/Register/RegisterViewModel.kt
package com.cardiovascularmodel.livingheart.Ui.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardiovascularmodel.livingheart.Auth.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Registro.
 * Mantiene el estado de usuario/contraseña y gestiona la llamada al repositorio.
 */
class RegisterViewModel(
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {

    // Estados para enlazar con los OutlinedTextField
    var username = mutableStateOf("")   // Aquí asumimos que username es email
        private set

    var password = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    // Métodos para actualizar los campos
    fun onUsernameChanged(newEmail: String) {
        username.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    /**
     * Intentar registrar usuario con Firebase Auth.
     * Al completarse, actualiza isLoading y errorMessage.
     */
    fun register(onSuccess: () -> Unit) {
        val email = username.value.trim()
        val pwd = password.value.trim()

        if (email.isEmpty() || pwd.isEmpty()) {
            errorMessage.value = "Por favor completa todos los campos."
            return
        }

        if (pwd.length < 6) {
            errorMessage.value = "La contraseña debe tener al menos 6 caracteres."
            return
        }

        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            val result = authRepo.registerWithEmailAndPassword(email, pwd)
            isLoading.value = false

            result.fold(
                onSuccess = { uid ->
                    // Registro exitoso
                    errorMessage.value = null
                    onSuccess()  // Llamamos al callback para navegar
                },
                onFailure = { ex ->
                    errorMessage.value = ex.message ?: "Error desconocido al registrarse."
                }
            )
        }
    }
}
