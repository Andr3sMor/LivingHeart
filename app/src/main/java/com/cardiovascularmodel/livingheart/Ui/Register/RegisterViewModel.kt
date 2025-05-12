package com.cardiovascularmodel.livingheart.Ui.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var username = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set

    fun onUsernameChanged(newValue: String) {
        username.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        password.value = newValue
    }

    fun onRegisterClicked() {
        // Aquí iría la lógica de validación o llamada al repositorio
    }

    fun onGoogleRegisterClicked() {
        // Aquí iría la lógica para Google Sign-In
    }
}