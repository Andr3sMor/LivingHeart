package com.cardiovascularmodel.livingheart.Ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardiovascularmodel.livingheart.R

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .background(Color(0xFF0A1E2D)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inicio Sesión",
            color = Color.White,
            fontSize = 38.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            fontFamily = FontFamily.SansSerif
        )

        // Campo de Usuario
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Iniciar
        Button(
            onClick = { /* Acción */ },
            modifier = Modifier
                .width(115.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Iniciar", color = Color.Black, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { /* Acción */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "OR",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            fontFamily = FontFamily.SansSerif
        )
        // Botón de Google
        OutlinedButton(
            onClick = { /* Acción */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icgoogle),
                contentDescription = "Google",
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 8.dp),
                tint = Color.Unspecified
            )
            Text("Continuar con Google", fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
