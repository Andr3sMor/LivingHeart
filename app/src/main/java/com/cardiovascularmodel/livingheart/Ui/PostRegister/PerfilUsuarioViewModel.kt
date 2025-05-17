package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PerfilUsuarioViewModel : ViewModel() {

    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")
    var peso by mutableStateOf("")
    var estatura by mutableStateOf("")
    var edad by mutableStateOf("")
    var actividadFisica by mutableStateOf("")

    var dropdownExpanded by mutableStateOf(false)

    val listaActividades = listOf("Sedentario", "Ligera", "Moderada", "Intensa")

}