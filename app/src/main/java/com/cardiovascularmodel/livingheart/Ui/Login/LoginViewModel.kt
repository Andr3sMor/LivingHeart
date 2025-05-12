package com.cardiovascularmodel.livingheart.Ui.Login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onUsernameChanged(newValue: String) {
        username = newValue
    }

    fun onPasswordChanged(newValue: String) {
        password = newValue
    }
}
