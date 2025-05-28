package com.cardiovascularmodel.livingheart.Ui.Drawer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.Navigation.currentRoute
import com.cardiovascularmodel.livingheart.Ui.Drawer.Items_drawer.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    navController: NavHostController,
     drawerState: DrawerState,
    contenido: @Composable () -> Unit
){
    val scope = rememberCoroutineScope()
    val drawer_items = listOf(
        Item_drawer1,
        Item_drawer2,
        Item_drawer3,
        Item_drawer4
    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF0F1E2E),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .border(1.dp, Color.White, shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            ) {
                // Botón hamburguesa
                IconButton(
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.Start)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Cerrar menú",
                        tint = Color.White
                    )
                }

                // Items del drawer
                drawer_items.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        },
                        selected = currentRoute(navController) == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        shape = RectangleShape,
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = Color(0xFF1A2B3C),
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón flotante inferior (opcional)
                IconButton(
                    onClick = { /* Acción de configuración o logout */ },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(36.dp)
                        .border(1.dp, Color.White, shape = RoundedCornerShape(4.dp))
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings, // O el que prefieras
                        contentDescription = "Ajustes",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        contenido()
    }
}