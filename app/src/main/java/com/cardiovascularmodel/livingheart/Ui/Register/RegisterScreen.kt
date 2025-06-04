// app/src/main/java/com/cardiovascularmodel/livingheart/Ui/Register/RegisterScreen.kt
package com.cardiovascularmodel.livingheart.Ui.Register

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.Navigation.AppScreens
import com.cardiovascularmodel.livingheart.R

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavHostController
) {
    val email by viewModel.username
    val password by viewModel.password
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .background(Color(0xFF0A1E2D)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Registrarse",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            fontFamily = FontFamily.SansSerif
        )

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text("Correo electrónico", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = Color.White),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            )
        )

        // Mensaje de error si existe
        errorMessage?.let { mensaje ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensaje, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Registrarse
        Button(
            onClick = {
                viewModel.register {
                    // Callback onSuccess: navegar a Dashboard u otra pantalla
                    navController.navigate(AppScreens.DashboardScreen.route) {
                        popUpTo(AppScreens.RegisterScreen.route) { inclusive = true }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .wrapContentWidth()
                .height(45.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Registrarse", color = Color.Black, fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Enlace a Login
        TextButton(
            onClick = { navController.navigate(AppScreens.Login.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Ya tienes una cuenta? Inicia Sesión",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Separador OR
        Text(
            text = "OR",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón “Continuar con Google” (puedes implementar después)
        OutlinedButton(
            onClick = { /* TODO: implementar Google Sign-In */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icgoogle),
                contentDescription = "Google",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp),
                tint = Color.Unspecified
            )
            Text("Continuar con Google", fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
        }
    }
}
