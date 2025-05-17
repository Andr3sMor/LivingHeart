package com.cardiovascularmodel.livingheart.Ui.Dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cardiovascularmodel.livingheart.R
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel(), navController: NavHostController) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    Drawer(
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold (
            topBar = { TopBar(drawerState) },
            containerColor = Color(0xFF0B1E2D)
        ){padding->
            Box(modifier = Modifier.padding(padding)){
                DashboardContent(viewModel)
            }
        }
    }
}

@Composable
fun DashboardContent(viewModel: DashboardViewModel) {
    val steps = viewModel.steps
    val stepsGoal = viewModel.stepsGoal
    val calories = viewModel.calories
    val caloriesGoal = viewModel.caloriesGoal
    val workoutMinutes = viewModel.workoutMinutes
    val workoutGoal = viewModel.workoutGoal
    val heartRate = viewModel.heartRate
    val hrv = viewModel.hrv
    val spo2 = viewModel.spo2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E2D))
            .padding(38.dp)
    ) {

        Text(
            text = "Dashboard",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Nuevo layout: Row para gráfico y leyenda
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressRings(
                stepsProgress = steps.toFloat() / stepsGoal,
                caloriesProgress = calories.toFloat() / caloriesGoal,
                workoutProgress = workoutMinutes.toFloat() / workoutGoal
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                LegendItem("Pasos", "$steps / $stepsGoal pasos", Color(0xFFFFA500))
                Spacer(modifier = Modifier.height(8.dp))
                LegendItem("Calorías", "$calories / $caloriesGoal kcal", Color(0xFF00C853))
                Spacer(modifier = Modifier.height(8.dp))
                LegendItem("Duración del entrenamiento", "$workoutMinutes / $workoutGoal minutos", Color(0xFF03A9F4))
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Frecuencia Cardiaca",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$heartRate",
            fontSize = 87.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(130.dp)
        ) {
            MetricItem("HRV", "$hrv ms")
            MetricItem("SpO2", "$spo2%")
        }
    }
}


@Composable
fun LegendItem(title: String, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = value,
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }
    }
}


@Composable
fun MetricItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 25.sp // Tamaño del título
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 31.sp, // Tamaño de la métrica
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProgressRings(
    stepsProgress: Float,
    caloriesProgress: Float,
    workoutProgress: Float
) {
    val ringSize = 140.dp
    val strokeWidth = 22f // Más grueso para mayor separación
    val spacing = 15f // Espacio extra entre anillos

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(ringSize)
    ) {
        Canvas(modifier = Modifier.size(ringSize)) {
            val canvasSize = size.minDimension

            // Primer anillo - Pasos (externo)
            drawArc(
                color = Color(0xFFFFA500),
                startAngle = -90f,
                sweepAngle = 360f * stepsProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Segundo anillo - Calorías
            val offset1 = strokeWidth + spacing
            drawArc(
                color = Color(0xFF00C853),
                startAngle = -90f,
                sweepAngle = 360f * caloriesProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offset1, offset1),
                size = Size(canvasSize - offset1 * 2, canvasSize - offset1 * 2)
            )

            // Tercer anillo - Entrenamiento (interno)
            val offset2 = strokeWidth * 2 + spacing * 2
            drawArc(
                color = Color(0xFF03A9F4),
                startAngle = -90f,
                sweepAngle = 360f * workoutProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offset2, offset2),
                size = Size(canvasSize - offset2 * 2, canvasSize - offset2 * 2)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.icreloj),
            contentDescription = null,
            modifier = Modifier.size(38.dp)
        )
    }
}

/*@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}
*/