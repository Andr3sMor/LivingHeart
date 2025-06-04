package com.cardiovascularmodel.livingheart.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cardiovascularmodel.livingheart.Ui.Dashboard.DashboardScreen
import com.cardiovascularmodel.livingheart.Ui.Login.LoginScreen
import com.cardiovascularmodel.livingheart.Ui.MedicalHistory.MedicalHistoryScreen
import com.cardiovascularmodel.livingheart.Ui.PostRegister.GoogleFitScreen
import com.cardiovascularmodel.livingheart.Ui.Register.RegisterScreen
import com.cardiovascularmodel.livingheart.Ui.RiskAssessment.RiskAssessmentScreen
import com.cardiovascularmodel.livingheart.Ui.Settings.SettingsScreen
import com.cardiovascularmodel.livingheart.Ui.SplashScreen.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController= navController,
        startDestination= AppScreens.SplashScreen.route
    ){
        composable(AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(AppScreens.Login.route){
            LoginScreen(navController = navController)
        }
        composable (AppScreens.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable (AppScreens.DashboardScreen.route){
            DashboardScreen(navController = navController)
        }
        composable (AppScreens.RiskAssessmentScreen.route){
            RiskAssessmentScreen(navController = navController)
        }
        composable (AppScreens.GoogleFitScreen.route){
            GoogleFitScreen(navController = navController)
        }
        composable(AppScreens.MedicalHistoryScreen.route){
            MedicalHistoryScreen(navController = navController)
        }
        composable (AppScreens.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route