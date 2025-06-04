package com.cardiovascularmodel.livingheart.Data.Fit

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DailyHealthUploader(private val context: Context) {

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_MOVE_MINUTES, FitnessOptions.ACCESS_READ)
        .build()

    private val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    fun collectAndUploadDailyData() {
        val startCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        val start = startCalendar.timeInMillis
        val end = endCalendar.timeInMillis

        val results = mutableMapOf<String, Any>()

        // Leer lista completa de frecuencia cardíaca
        readHeartRateList(start, end) { heartRateList ->
            results["frecuenciaCardiaca"] = heartRateList

            // Leer promedio de pasos, calorías y duración
            val types = listOf(
                DataType.TYPE_STEP_COUNT_DELTA,
                DataType.TYPE_CALORIES_EXPENDED,
                DataType.TYPE_MOVE_MINUTES
            )

            var readCount = 0

            types.forEach { type ->
                readDailyAverage(type, start, end) { value ->
                    results[type.name] = value
                    readCount++
                    if (readCount == types.size) {
                        // Agrega HRV y SpO2 simulados
                        results["hrv"] = generateRandomHRV()
                        results["spo2"] = generateRandomSpO2()

                        uploadToFirestore(results)
                    }
                }
            }
        }
    }

    private fun readHeartRateList(start: Long, end: Long, onResult: (List<Float>) -> Unit) {
        val request = DataReadRequest.Builder()
            .read(DataType.TYPE_HEART_RATE_BPM)
            .setTimeRange(start, end, TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, account)
            .readData(request)
            .addOnSuccessListener { response ->
                val heartRates = mutableListOf<Float>()
                response.dataSets.forEach { dataSet ->
                    dataSet.dataPoints.forEach { point ->
                        try {
                            val bpm = point.getValue(Field.FIELD_BPM).asFloat()
                            heartRates.add(bpm)
                        } catch (_: Exception) {
                        }
                    }
                }
                onResult(heartRates)
            }
            .addOnFailureListener {
                Log.e("DailyHealthUploader", "Error leyendo frecuencia cardíaca: ${it.message}")
                onResult(emptyList())
            }
    }

    private fun readDailyAverage(dataType: DataType, start: Long, end: Long, onResult: (Float) -> Unit) {
        val request = DataReadRequest.Builder()
            .read(dataType)
            .setTimeRange(start, end, TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, account)
            .readData(request)
            .addOnSuccessListener { response ->
                val values = response.getDataSet(dataType).dataPoints.mapNotNull {
                    try {
                        it.getValue(dataType.fields[0]).asFloat()
                    } catch (_: Exception) {
                        null
                    }
                }
                val avg = if (values.isNotEmpty()) values.average().toFloat() else 0f
                onResult(avg)
            }
            .addOnFailureListener {
                Log.e("DailyHealthUploader", "Error leyendo ${dataType.name}: ${it.message}")
                onResult(0f)
            }
    }

    private fun uploadToFirestore(data: Map<String, Any>) {
        val fecha = Calendar.getInstance().time.toString()

        val payload = mutableMapOf<String, Any>()
        payload["fecha"] = fecha
        payload["frecuenciaCardiaca"] = data["frecuenciaCardiaca"] ?: emptyList<Float>()
        payload["hrv"] = data["hrv"] ?: 0f
        payload["spo2"] = data["spo2"] ?: 0f
        payload["pasos"] = (data[DataType.TYPE_STEP_COUNT_DELTA.name] as? Float)?.toInt() ?: 0
        payload["calorias"] = data[DataType.TYPE_CALORIES_EXPENDED.name] ?: 0f
        payload["duracion"] = (data[DataType.TYPE_MOVE_MINUTES.name] as? Float)?.toInt() ?: 0

        Firebase.firestore.collection("historial")
            .add(payload)
            .addOnSuccessListener {
                Log.d("DailyHealthUploader", "Datos subidos correctamente")
            }
            .addOnFailureListener {
                Log.e("DailyHealthUploader", "Error subiendo datos: ${it.message}")
            }
    }

    private fun generateRandomHRV(): Float {
        return Random.nextDouble(20.0, 100.0).toFloat()
    }

    private fun generateRandomSpO2(): Float {
        return Random.nextDouble(95.0, 100.0).toFloat()
    }
}
