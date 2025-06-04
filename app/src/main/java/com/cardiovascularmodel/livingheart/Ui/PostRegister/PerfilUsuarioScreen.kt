package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(viewModel: PerfilUsuarioViewModel = PerfilUsuarioViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0A1E2C)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
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
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 45.sp),
                    colors = textFieldColors()
                )
                OutlinedTextField(
                    value = viewModel.apellido,
                    onValueChange = { viewModel.apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp), // Aumentando la altura
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Aumentando tamaño de texto
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
                        .height(60.dp), // Aumentando la altura
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Aumentando tamaño de texto
                    colors = textFieldColors()
                )
                OutlinedTextField(
                    value = viewModel.estatura,
                    onValueChange = { viewModel.estatura = it },
                    label = { Text("Estatura") },
                    trailingIcon = { Text("Cm", color = Color.White) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp), // Aumentando la altura
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Aumentando tamaño de texto
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
                    .height(60.dp), // Aumentando la altura
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Aumentando tamaño de texto
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
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(60.dp), // Aumentando la altura
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Aumentando tamaño de texto
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
                onClick = {},
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
        }
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
    unfocusedLabelColor = Color.White
)

@Preview(showBackground = true)
@Composable
fun PreviewPerfilUsuarioScreen() {
    PerfilUsuarioScreen()
}

