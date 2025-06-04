package com.cardiovascularmodel.livingheart.Ui.Dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
            topBar = { TopBar(drawerState, navController) },
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
    val userName = viewModel.userName

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
            .padding(36.dp)
    ) {

        Text(
            text = "Dashboard",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,

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
                fontSize = 14.sp
            )
            Text(
                text = value,
                color = Color.LightGray,
                fontSize = 11.sp
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
*/// DashboardScreen.kt (DESPUÉS DE LOS CAMBIOS - Integrando Perfil)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen( // Ya no necesitarías onNavigateToSettings si todo está aquí
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val scrollState = rememberScrollState() // Añadir para scroll si el contenido crece

    // Estados para los campos de texto editables del perfil
    var editingUserName by remember { mutableStateOf(dashboardViewModel.userName) }
    var editingUserEmail by remember { mutableStateOf(dashboardViewModel.userEmail) }

    // Estados para controlar si se está editando el perfil
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard y Perfil") }, // Título actualizado
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState), // Habilitar scroll
            verticalArrangement = Arrangement.spacedBy(20.dp) // Espaciado general
        ) {
            // --- SECCIÓN DE MÉTRICAS DE SALUD (Tu contenido existente) ---
            Text("Métricas de Salud", style = MaterialTheme.typography.titleLarge)
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Pasos: ${dashboardViewModel.steps} / ${dashboardViewModel.stepsGoal}")
                    Text("Calorías: ${dashboardViewModel.calories} / ${dashboardViewModel.caloriesGoal}")
                    Text("Minutos de Ejercicio: ${dashboardViewModel.workoutMinutes} / ${dashboardViewModel.workoutGoal}")
                    Text("Ritmo Cardíaco: ${dashboardViewModel.heartRate} bpm")
                    // ... otras métricas
                }
            }


            // --- NUEVA SECCIÓN: PERFIL DEL USUARIO ---
            Text(
                text = "Información del Usuario",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EditableTextField( // Reutiliza el Composable EditableTextField
                        label = "Nombre",
                        value = editingUserName,
                        onValueChange = { editingUserName = it },
                        isEditing = isEditingName,
                        onEditToggle = {
                            if (isEditingName) {
                                dashboardViewModel.updateUserName(editingUserName)
                            } else {
                                editingUserName = dashboardViewModel.userName
                            }
                            isEditingName = !isEditingName
                        },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nombre") }
                    )

                    EditableTextField( // Reutiliza el Composable EditableTextField
                        label = "Email",
                        value = editingUserEmail,
                        onValueChange = { editingUserEmail = it },
                        isEditing = isEditingEmail,
                        onEditToggle = {
                            if (isEditingEmail) {
                                dashboardViewModel.updateUserEmail(editingUserEmail)
                            } else {
                                editingUserEmail = dashboardViewModel.userEmail
                            }
                            isEditingEmail = !isEditingEmail
                        },
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        )
                    )
                }
            }

            // --- NUEVA SECCIÓN: CONFIGURACIÓN DE NOTIFICACIONES ---
            Text(
                text = "Configuración de Notificaciones",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    NotificationSwitchRow( // Reutiliza el Composable NotificationSwitchRow
                        text = "Activar todas las notificaciones",
                        checked = dashboardViewModel.notificationsEnabled,
                        onCheckedChange = { dashboardViewModel.toggleNotifications(it) },
                        icon = {
                            Icon(
                                Icons.Filled.Notifications,
                                contentDescription = "Notificaciones"
                            )
                        }
                    )

                    if (dashboardViewModel.notificationsEnabled) {
                        Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                            NotificationSwitchRow(
                                text = "Recordatorios de actividad",
                                checked = dashboardViewModel.activityReminderNotifications,
                                onCheckedChange = {
                                    dashboardViewModel.toggleActivityReminderNotifications(
                                        it
                                    )
                                }
                            )
                            NotificationSwitchRow(
                                text = "Logros alcanzados",
                                checked = dashboardViewModel.achievementNotifications,
                                onCheckedChange = {
                                    dashboardViewModel.toggleAchievementNotifications(
                                        it
                                    )
                                }
                            )
                            NotificationSwitchRow(
                                text = "Actualizaciones de la aplicación",
                                checked = dashboardViewModel.appUpdateNotifications,
                                onCheckedChange = {
                                    dashboardViewModel.toggleAppUpdateNotifications(
                                        it
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- DEBES COPIAR O IMPORTAR ESTOS COMPOSABLES AUXILIARES ---
// (EditableTextField y NotificationSwitchRow que te proporcioné antes)
// Si los pones en el mismo archivo, está bien. Si los pones en un archivo separado,
// asegúrate de importarlos.
@Composable
fun EditableTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
    onEditToggle: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = leadingIcon,
            modifier = Modifier.weight(1f),
            readOnly = !isEditing,
            keyboardOptions = keyboardOptions,
            singleLine = true
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onEditToggle) {
            Text(if (isEditing) "Guardar" else "Editar")
        }
    }
}

@Composable
fun NotificationSwitchRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(text, style = MaterialTheme.typography.bodyLarge)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        )
    }
}