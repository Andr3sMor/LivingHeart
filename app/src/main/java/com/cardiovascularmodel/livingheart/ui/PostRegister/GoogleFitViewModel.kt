package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GoogleFitViewModel : ViewModel() {
    private var _isConnected by mutableStateOf(false)

    val isConnected: Boolean get() = _isConnected

    fun connectToGoogleFit() {
        // Lógica real de conexión iría aquí
        _isConnected = true
    }
}

