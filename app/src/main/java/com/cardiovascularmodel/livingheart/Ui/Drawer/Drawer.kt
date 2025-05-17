package com.cardiovascularmodel.livingheart.Ui.Drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
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
        Item_drawer2
    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                drawer_items.forEach {item->
                    NavigationDrawerItem(
                        icon = {
                            Icon(item.icon, null)
                        },
                        label = { Text(text = item.title, fontWeight = FontWeight.Bold) },
                        selected = currentRoute(navController) == item.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.route)
                        }
                    )
                }
            }
        }
    ) {
        contenido()
    }
}