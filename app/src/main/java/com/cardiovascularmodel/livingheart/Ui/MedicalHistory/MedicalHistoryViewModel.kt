package com.cardiovascularmodel.livingheart.Ui.MedicalHistory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class MedicalRecord(
    val date: String,
    val status: String,
    val risk: Int,
    val diagnosis: String? = null,
    val restingHR: Int? = null,
    val hrv: Int? = null,
    val spo2: Int? = null,
    val irregularEvents: Int? = null,
    val ecgData: List<Float>? = null,
    val recommendations: String? = null,
    var isExpanded: Boolean = false
)

class MedicalHistoryViewModel : ViewModel() {
    var records by mutableStateOf(
        listOf(
            MedicalRecord("Apr 22, 2024", "Riesgo Moderado", 72, "Arritmia Leve", 92, 18, 94, 2, listOf(0f, 1.2f, 0.8f, 1.5f, 1f), "Programe cita con su cardiólogo"),
            MedicalRecord("Apr 15, 2024", "Normal", 65),
            MedicalRecord("Ene 10, 2024", "Riesgo Alto", 82),
            MedicalRecord("Ene 5, 2024", "Riesgo Moderado", 70)
        )
    )

    fun toggleExpanded(index: Int) {
        records = records.mapIndexed { i, record ->
            if (i == index) record.copy(isExpanded = !record.isExpanded) else record.copy(isExpanded = false)
        }
    }
}
