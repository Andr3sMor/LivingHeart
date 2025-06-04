package com.cardiovascularmodel.livingheart.Ui.Settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar

@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Drawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = { TopBar(drawerState, navController) },
            containerColor = Color(0xFF0B1E2D)
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                SettingsContent(viewModel)
            }
        }
    }
}

@Composable
fun SettingsContent(viewModel: SettingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E2D))
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Configuración",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        SettingItem(
            title = "Google Fit",
            description = "Conectado",
            onClick = { /* Acción futura */ }
        )

        Spacer(modifier = Modifier.height(30.dp))

        SettingItem(
            title = "Frecuencia de análisis",
            description = "Cada 12 horas",
            onClick = { /* Acción futura */ }
        )

        Spacer(modifier = Modifier.height(30.dp))

        SettingItem(
            title = "Unidades",
            description = "(kg, ms, km, bpm)",
            onClick = { /* Acción futura */ }
        )

        Spacer(modifier = Modifier.height(30.dp))

        SettingItem(
            title = "Contacto de Emergencia",
            description = "_______________",
            onClick = { /* Acción futura */ }
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(text = description, fontSize = 15.sp)
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Black
        )
    }
}
