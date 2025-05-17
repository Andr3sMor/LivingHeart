package com.cardiovascularmodel.livingheart.Ui.Dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    var steps by mutableStateOf(91)
        private set

    var stepsGoal by mutableStateOf(121)
        private set

    var calories by mutableStateOf(251)
        private set

    var caloriesGoal by mutableStateOf(301)
        private set

    var workoutMinutes by mutableStateOf(15)
        private set

    var workoutGoal by mutableStateOf(31)
        private set

    var heartRate by mutableStateOf(72)
        private set

    var hrv by mutableStateOf(55)
        private set

    var spo2 by mutableStateOf(97)
        private set
}