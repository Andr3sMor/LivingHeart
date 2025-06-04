package com.cardiovascularmodel.livingheart.Ui.RiskAssessment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cardiovascularmodel.livingheart.R
import com.cardiovascularmodel.livingheart.Ui.Drawer.Drawer
import com.cardiovascularmodel.livingheart.Ui.Drawer.TopBar


@Composable
fun RiskAssessmentScreen(viewModel: RiskAssessmentViewModel = viewModel(), navController: NavHostController){
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    Drawer(
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold (
            topBar = { TopBar(drawerState, navController) },
            containerColor = Color(0xFF0B1E2D)
        ){padding->
            Box(modifier = Modifier.padding(padding)){
                RiskAssessmentContent(viewModel)
            }
        }
    }
}

@Composable
fun RiskAssessmentContent(viewModel: RiskAssessmentViewModel) {
    val state = viewModel.riskState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E2D))
            .padding(34.dp)
    ) {
        Text(
            text = "Evaluacion de",
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Riesgos",
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(45.dp))

        // Nivel de riesgo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Column {
                // Título dentro del contenedor
                Text(
                    text = "Nivel de riesgo",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(state.riskColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = state.riskLevel,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Condición
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Condición",
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${state.conditionPercentage}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sangría por imagen
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.estetoscopio),
                        contentDescription = "Ícono condición",
                        modifier = Modifier
                            .size(34.dp)
                            .padding(end = 8.dp)
                    )

                    Column {
                        Text(
                            text = state.condition,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = state.conditionDescription,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Recomendación
        // Recomendación
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Recomendación",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.chinche),
                        contentDescription = "Ícono recomendación",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                    )

                    Text(
                        text = state.recommendation,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
/*
@Composable
@Preview(showBackground = true)
fun RiskAssessmentPreview() {
    RiskAssessmentScreen()
}*/
