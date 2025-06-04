package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoogleFitViewModel : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    fun setConnected(connected: Boolean) {
        _isConnected.value = connected
    }

}