package com.cardiovascularmodel.livingheart.Ui.PostRegister // Asegúrate que el package sea el correcto

import android.app.Application // Necesario si tu ViewModel es AndroidViewModel para el Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer // Asume que existe este Composable
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar   // Asume que existe este Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    navController: NavHostController, // Añadido NavController
    viewModel: PerfilUsuarioViewModel = viewModel() // Obtener ViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Similar a MedicalHistoryScreen, envolvemos el contenido con el Drawer
    Drawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = { TopBar(drawerState, navController) }, // Usamos el TopBar
            containerColor = Color(0xFF0A1E2C) // Color de fondo del Scaffold
        ) { paddingValues ->
            // Contenido principal de la pantalla del perfil
            PerfilUsuarioContent(
                modifier = Modifier
                    .padding(paddingValues) // Aplicar padding del Scaffold
                    .fillMaxSize(),
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioContent(
    modifier: Modifier = Modifier,
    viewModel: PerfilUsuarioViewModel
) {
    // Usamos LaunchedEffect para cargar el perfil una vez al entrar a la pantalla
    LaunchedEffect(key1 = Unit) {
        viewModel.loadUserProfile()
    }

    Column(
        modifier = modifier
            .background(Color(0xFF0A1E2C)) // Color de fondo del contenido
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()), // Permite el scroll si el contenido es largo
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp)) // Ajusta según diseño, similar a MedicalHistory

        Text(
            text = "Perfil De",
            color = Color.White,
            fontSize = 45.sp, // Ajusta según tus preferencias
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif // Puedes mantener tu fuente o alinearla
        )
        Text(
            text = "Usuario",
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(bottom = 25.dp) // Espacio después del título
        )

        // Nombre y Apellido
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 60.dp), // Usar heightIn para flexibilidad
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp), // Ajustar tamaño de texto
                colors = textFieldColors(),
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.apellido,
                onValueChange = { viewModel.apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                colors = textFieldColors(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Peso y Estatura
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.peso,
                onValueChange = { viewModel.peso = it },
                label = { Text("Peso") },
                trailingIcon = { Text("Kg", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                colors = textFieldColors(),
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.estatura,
                onValueChange = { viewModel.estatura = it },
                label = { Text("Estatura") },
                trailingIcon = { Text("Cm", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                colors = textFieldColors(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Edad
        OutlinedTextField(
            value = viewModel.edad,
            onValueChange = { viewModel.edad = it },
            label = { Text("Edad") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            colors = textFieldColors(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Actividad Física Dropdown
        ExposedDropdownMenuBox(
            expanded = viewModel.dropdownExpanded,
            onExpandedChange = { viewModel.dropdownExpanded = !viewModel.dropdownExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.actividadFisica,
                onValueChange = {}, // No se cambia directamente aquí
                readOnly = true,
                label = { Text("Actividad Física") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.dropdownExpanded)
                },
                modifier = Modifier
                    .menuAnchor() // Importante para ExposedDropdownMenuBox
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                colors = textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = viewModel.dropdownExpanded,
                onDismissRequest = { viewModel.dropdownExpanded = false },
                modifier = Modifier.background(Color(0xFF1A3649)) // Color de fondo del menú
            ) {
                viewModel.listaActividades.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion, color = Color.White) },
                        onClick = {
                            viewModel.actividadFisica = opcion
                            viewModel.dropdownExpanded = false
                        },
                        colors = MenuDefaults.itemColors(

                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Guardar (anteriormente "Continuar")
        Button(
            onClick = {
                viewModel.saveUserProfile() // Llamar a la función para guardar
                // Aquí podrías añadir lógica para mostrar un Snackbar/Toast o navegar
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Guardar Cambios",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(24.dp)) // Espacio al final para el scroll
    }
}

// textFieldColors se mantiene igual, pero podrías considerar moverlo a un archivo de Theme o Utils
@Composable
fun textFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color.White.copy(alpha = 0.8f),
    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
    cursorColor = Color.White,
    focusedLabelColor = Color.White.copy(alpha = 0.8f),
    unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
    focusedTrailingIconColor = Color.White.copy(alpha = 0.8f),
    unfocusedTrailingIconColor = Color.White.copy(alpha = 0.5f),
    // Para el fondo del TextField si quieres que sea diferente al del Surface
    // P.ej., containerColor = Color.Transparent o un color ligeramente diferente
    // En este caso, OutlinedTextField es transparente por defecto en su área de contenido
)

@Preview(showBackground = true, backgroundColor = 0xFF0A1E2C) // Para que el preview se vea bien
@Composable
fun PreviewPerfilUsuarioScreen() {
    // Para el preview, necesitas un NavController y un ViewModel (puede ser uno dummy o el real con Application si es AndroidViewModel)
    val navController = rememberNavController()
    // Si PerfilUsuarioViewModel es un AndroidViewModel:
    // val dummyApplication = Application()
    // val previewViewModel = PerfilUsuarioViewModel(dummyApplication)
    // PerfilUsuarioScreen(navController = navController, viewModel = previewViewModel)

    // Si PerfilUsuarioViewModel es un ViewModel normal:
    PerfilUsuarioScreen(navController = navController, viewModel = PerfilUsuarioViewModel())
}