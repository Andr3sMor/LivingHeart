// app/src/main/java/com/cardiovascularmodel/livingheart/Ui/Login/LoginViewModel.kt
package com.cardiovascularmodel.livingheart.Ui.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardiovascularmodel.livingheart.Auth.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Login.
 * Mantiene el estado de usuario/contraseña y gestiona la llamada al repositorio.
 */
class LoginViewModel(
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {

    // Estados que enlazamos con los OutlinedTextField
    var username = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    // Estado que indica si hay un error al loguear o si está cargando
    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    // Método para manejar cambios en el campo usuario
    fun onUsernameChanged(newEmail: String) {
        username.value = newEmail
    }

    // Método para manejar cambios en el campo contraseña
    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    /**
     * Intentar iniciar sesión con Firebase Auth.
     * Al completarse, actualiza isLoading y errorMessage.
     */
    fun login(onSuccess: () -> Unit) {
        val email = username.value.trim()
        val pwd = password.value.trim()

        if (email.isEmpty() || pwd.isEmpty()) {
            errorMessage.value = "Por favor completa todos los campos."
            return
        }

        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            val result = authRepo.signInWithEmailAndPassword(email, pwd)
            isLoading.value = false

            result.fold(
                onSuccess = { uid ->
                    // Inicio de sesión exitoso
                    errorMessage.value = null
                    onSuccess()  // Llamamos al callback para navegar
                },
                onFailure = { ex ->
                    errorMessage.value = ex.message ?: "Error desconocido al iniciar sesión."
                }
            )
        }
    }
}
