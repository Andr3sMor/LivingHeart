package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer // Asegúrate que la importación sea correcta
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar // Asegúrate que la importación sea correcta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    navController: NavHostController, // Añadido NavHostController
    viewModel: PerfilUsuarioViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Drawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = { TopBar(drawerState, navController) }, // Asumiendo que TopBar también usa navController
            containerColor = Color(0xFF0A1E2C) // Color de fondo del Scaffold
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                PerfilUsuarioContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioContent(viewModel: PerfilUsuarioViewModel) {
    // El Surface ya no es necesario aquí si el Scaffold tiene el containerColor
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Para permitir el scroll si el contenido es largo
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp)) // Ajustado para el TopBar
        Text(
            text = "Perfil De\nUsuario",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 50.sp,
                lineHeight = 50.sp,
                fontFamily = FontFamily.SansSerif
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 37.dp)
        )

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
                    .height(60.dp),
                // textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 45.sp), // Parece un error tipográfico, 20sp es más consistente
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                colors = textFieldColors()
            )
            OutlinedTextField(
                value = viewModel.apellido,
                onValueChange = { viewModel.apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                colors = textFieldColors()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.peso,
                onValueChange = { viewModel.peso = it },
                label = { Text("Peso") },
                trailingIcon = { Text("Kg", color = Color.White) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                colors = textFieldColors()
            )
            OutlinedTextField(
                value = viewModel.estatura,
                onValueChange = { viewModel.estatura = it },
                label = { Text("Estatura") },
                trailingIcon = { Text("Cm", color = Color.White) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                colors = textFieldColors()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.edad,
            onValueChange = { viewModel.edad = it },
            label = { Text("Edad") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = viewModel.dropdownExpanded,
            onExpandedChange = { viewModel.dropdownExpanded = !viewModel.dropdownExpanded }
        ) {
            OutlinedTextField(
                value = viewModel.actividadFisica,
                onValueChange = {},
                readOnly = true,
                label = { Text("Actividad Física") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.dropdownExpanded)
                },
                modifier = Modifier
                    .menuAnchor() // menuAnchor se aplica al TextField dentro de ExposedDropdownMenuBox
                    .fillMaxWidth()
                    .height(60.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                colors = textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = viewModel.dropdownExpanded,
                onDismissRequest = { viewModel.dropdownExpanded = false }
            ) {
                viewModel.listaActividades.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            viewModel.actividadFisica = opcion
                            viewModel.dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: Implementar acción de continuar */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Continuar",
                fontSize = 17.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp)) // Espacio al final para el scroll
    }
}

@Composable
fun textFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.White,
    cursorColor = Color.White,
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White,
    // Podrías necesitar configurar también los colores del contenedor del TextField si el fondo por defecto no es transparente
    // focusedContainerColor = Color.Transparent, // O el color que desees
    // unfocusedContainerColor = Color.Transparent // O el color que desees
)

@Preview(showBackground = true, backgroundColor = 0xFF0A1E2C) // Color de fondo para la preview
@Composable
fun PreviewPerfilUsuarioScreen() {
    // Para previsualizar con el Drawer y TopBar, necesitarías un NavController real.
    // Para una preview más simple del contenido:
    // PerfilUsuarioContent(viewModel = PerfilUsuarioViewModel())

    // Si quieres previsualizar la pantalla completa (requiere que Drawer y TopBar no fallen con un NavController dummy)
    val navController = rememberNavController()
    PerfilUsuarioScreen(navController = navController, viewModel = PerfilUsuarioViewModel())
}