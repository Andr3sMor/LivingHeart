package com.cardiovascularmodel.livingheart.Ui.MedicalHistory

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar

@Composable
fun MedicalHistoryScreen(navController: NavHostController, viewModel: MedicalHistoryViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Drawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = { TopBar(drawerState, navController) },
            containerColor = Color(0xFF0B1E2D)
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                MedicalHistoryContent(viewModel)
            }
        }
    }
}

@Composable
fun MedicalHistoryContent(viewModel: MedicalHistoryViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E2D))
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Historial",
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Médico",
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            viewModel.records.forEachIndexed { index, record ->
                MedicalRecordCard(record) { viewModel.toggleExpanded(index) }
                Spacer(modifier = Modifier.height(35.dp))
            }
        }
    }
}

@Composable
fun MedicalRecordCard(record: MedicalRecord, onToggle: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(record.date, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text(record.status, fontWeight = FontWeight.Bold, fontSize = 19.sp)
            }
            Text("${record.risk}%", fontSize = 19.sp)
        }

        if (record.isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("❤️ Diagnóstico: ${record.diagnosis}", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 17.sp)

            Spacer(modifier = Modifier.height(8.dp))
            MetricRow("FC Reposo", "${record.restingHR} bpm", Color.Red)
            MetricRow("HRV", "${record.hrv} ms", Color.Red)
            MetricRow("SpO₂", "${record.spo2}%", Color(0xFFFFA500))
            MetricRow("Ritmo Irregular", "${record.irregularEvents} eventos", Color(0xFFFFA500))

            Spacer(modifier = Modifier.height(12.dp))
            Text("ECG", fontWeight = FontWeight.Bold)
            ECGGraph(ecgData = record.ecgData ?: emptyList(), isArrhythmia = record.status != "Normal")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Recomendaciones", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(record.recommendations ?: "Ninguna", fontSize = 17.sp)
        }
    }
}

@Composable
fun MetricRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
        Text(
            text = value,
            fontSize = 17.sp
        )
    }
}

@Composable
fun ECGGraph(ecgData: List<Float>, isArrhythmia: Boolean) {
    val lineColor = if (isArrhythmia) Color(0xFFFF4C4C) else Color(0xFF2196F3) // azul para normales

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp)
    ) {
        if (ecgData.isNotEmpty()) {
            val spacing = size.width / (ecgData.size - 1).coerceAtLeast(1)
            val maxVal = ecgData.maxOrNull() ?: 1f
            val minVal = ecgData.minOrNull() ?: 0f
            val range = maxVal - minVal

            val points = ecgData.mapIndexed { index, value ->
                val x = index * spacing
                val y = if (range == 0f) {
                    size.height / 2f
                } else {
                    size.height - ((value - minVal) / range * size.height)
                }
                Offset(x, y)
            }

            for (i in 0 until points.size - 1) {
                drawLine(
                    color = lineColor,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 2f
                )
            }
        }
    }
}
