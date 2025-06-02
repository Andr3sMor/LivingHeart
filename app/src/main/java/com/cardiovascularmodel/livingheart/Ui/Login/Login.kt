// app/src/main/java/com/cardiovascularmodel/livingheart/Ui/Login/LoginScreen.kt
package com.cardiovascularmodel.livingheart.Ui.Login

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
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavHostController
) {
    val email by viewModel.username
    val password by viewModel.password
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Contenedor principal
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
            text = "Iniciar Sesión",
            color = Color.White,
            fontSize = 38.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            fontFamily = FontFamily.SansSerif
        )

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text("Correo electrónico", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.SansSerif) },
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
            label = { Text("Contraseña", color = Color.White, fontSize = 18.sp, fontFamily = FontFamily.SansSerif) },
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

        // Mostrar mensaje de error si existe
        errorMessage?.let { mensaje ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensaje, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Iniciar Sesión
        Button(
            onClick = {
                // Llamamos a la función login y si es exitosa, navegamos a Dashboard.
                viewModel.login {
                    // Callback onSuccess: navegar a Dashboard
                    navController.navigate(AppScreens.DashboardScreen.route) {
                        // opcional: limpiar el back stack para que no vuelva a Login
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .width(140.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Iniciar", color = Color.Black, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace a Registro
        TextButton(
            onClick = {
                navController.navigate(AppScreens.RegisterScreen.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate",
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
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 16.dp),
            fontFamily = FontFamily.SansSerif
        )

        // Botón “Continuar con Google” (puedes implementar más adelante)
        OutlinedButton(
            onClick = { /* TODO: implementar Google Sign-In si quieres */ },
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
                    .size(35.dp)
                    .padding(end = 8.dp),
                tint = Color.Unspecified
            )
            Text("Continuar con Google", fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        }
    }
}
