package com.cardiovascularmodel.livingheart.Ui.PostRegister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.R
import com.cardiovascularmodel.livingheart.Ui.SplashScreen.Splash

@Composable
fun GoogleFitScreen(viewModel: GoogleFitViewModel = viewModel(), navController: NavHostController) {
    val isConnected = viewModel.isConnected

    // Si ya está conectado, podrías navegar a la siguiente pantalla
    if (isConnected) {
        // Navega a otra pantalla o muestra una confirmación
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B2A)), // Fondo oscuro
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Google Fit",
                color = Color.White,
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Requerido",
                color = Color.White,
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Porfavor conectate a Google Fit\npara continuar",
                color = Color.LightGray,
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(75.dp))
            Button(
                onClick = { viewModel.connectToGoogleFit() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icgooglefit),
                    contentDescription = "Google Fit Icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified // usa el color original del ícono
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Conectar a Google Fit",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}











































































































