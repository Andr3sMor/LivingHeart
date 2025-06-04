package com.cardiovascularmodel.livingheart.Ui.PostRegister // Or wherever your ViewModel is located

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PerfilUsuarioViewModel : ViewModel() {

    // Properties to hold user profile data
    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")
    var peso by mutableStateOf("")
    var estatura by mutableStateOf("")
    var edad by mutableStateOf("")
    var actividadFisica by mutableStateOf("Selecciona una opción") // Default value
    var dropdownExpanded by mutableStateOf(false)

    val listaActividades = listOf(
        "Sedentario (poco o ningún ejercicio)",
        "Ligero (ejercicio ligero 1-3 días/semana)",
        "Moderado (ejercicio moderado 3-5 días/semana)",
        "Activo (ejercicio intenso 6-7 días/semana)",
        "Muy activo (ejercicio muy intenso y trabajo físico)"
    )

    // Function to load user profile data
    fun loadUserProfile() {
        viewModelScope.launch {
            // TODO: Implement the logic to load user data
            // For example, from a database, SharedPreferences, or a network request.
            // For now, let's assume some dummy data or previously saved data.
            // nombre = "John"
            // apellido = "Doe"
            // ... etc.
        }
    }

    // Function to save user profile data
    fun saveUserProfile() {
        viewModelScope.launch {
            // TODO: Implement the logic to save user data
            // For example, to a database, SharedPreferences, or a network request.
            // Log.d("PerfilUsuarioViewModel", "Nombre: $nombre, Apellido: $apellido, Peso: $peso, Estatura: $estatura, Edad: $edad, Actividad: $actividadFisica")
        }
    }
}