package com.cardiovascularmodel.livingheart.Ui.Drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardiovascularmodel.livingheart.Navigation.AppScreens

sealed class Items_drawer(
    val icon: ImageVector,
    val title: String,
    val route: String
){
    object Item_drawer1: Items_drawer(
        Icons.Outlined.CheckCircle,
        "Página Principal",
        AppScreens.DashboardScreen.route
    )
    object Item_drawer2: Items_drawer(
        Icons.Outlined.CheckCircle,
        "Usuario",
        AppScreens.PerfilUsuarioScreen.route
    )
    object Item_drawer3: Items_drawer(
        Icons.Outlined.CheckCircle,
        "Ev. Riesgos",
        AppScreens.RiskAssessmentScreen.route
    )
    object Item_drawer4: Items_drawer(
        Icons.Outlined.CheckCircle,
        "Historial Médico",
        AppScreens.MedicalHistoryScreen.route
    )
    object Item_drawer5: Items_drawer(
        Icons.Outlined.CheckCircle,
        "Google Fit",
        AppScreens.GoogleFitScreen.route
    )
}