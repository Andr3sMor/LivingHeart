package com.cardiovascularmodel.livingheart.Ui.RiskAssessment

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RiskState(
    val riskLevel: String = "Riesgo Moderado",
    val riskColor: Color = Color.Yellow,
    val condition: String = "Arritmia (Fibrilación Auricular)",
    val conditionPercentage: Int = 72,
    val conditionDescription: String = "HRV irregular detectado y un ritmo cardiaco inconsistente",
    val recommendation: String = "Programe una cita con su cardiólogo"
)

class RiskAssessmentViewModel : ViewModel() {
    private val _riskState = MutableStateFlow(RiskState())
    val riskState: StateFlow<RiskState> = _riskState
}
