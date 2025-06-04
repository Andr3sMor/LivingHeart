package com.cardiovascularmodel.livingheart.Ui.Drawer

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cardiovascularmodel.livingheart.Navigation.AppScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    drawerState: DrawerState,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir Menu",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute == AppScreens.SettingsScreen.route) {
                    // ✅ Volver a la anterior pantalla
                    navController.popBackStack()
                } else {
                    // ✅ Navegar a settings sin duplicar y permitiendo volver correctamente
                    navController.navigate(AppScreens.SettingsScreen.route) {
                        launchSingleTop = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF0B1E2D), // Cambia este color de fondo
            navigationIconContentColor = Color.White,
        )
    )
}
