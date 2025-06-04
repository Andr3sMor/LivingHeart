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

val healthyRecord = MedicalRecord(
    date = "Mayo 21, 2025", // hace dos semanas
    status = "Normal",
    risk = 32,
    diagnosis = "Sin Anomalías",
    restingHR = 68,
    hrv = 35,
    spo2 = 98,
    irregularEvents = 0,
    ecgData = listOf(68f, 70f, 72f, 74f, 73f, 71f, 69f, 68f, 70f), // simulación simple
    recommendations = "Continúe con sus hábitos saludables",
    isExpanded = false
)

val arrhythmiaRecord = MedicalRecord(
    date = "Mayo 28, 2025", // hace una semana
    status = "Riesgo Moderado",
    risk = 75,
    diagnosis = "Arritmia detectada",
    restingHR = 92,
    hrv = 18,
    spo2 = 93,
    irregularEvents = 6,
    ecgData = listOf(90f, 130f, 85f, 170f, 100f, 160f, 90f, 140f), // valores simulando picos irregulares
    recommendations = "Programe una cita con su cardiólogo para evaluación ECG detallada",
    isExpanded = false
)


class MedicalHistoryViewModel : ViewModel() {
    var records by mutableStateOf(
        listOf(
            healthyRecord,
            arrhythmiaRecord
        )
    )

    fun toggleExpanded(index: Int) {
        records = records.mapIndexed { i, record ->
            if (i == index) record.copy(isExpanded = !record.isExpanded) else record.copy(isExpanded = false)
        }
    }
}
