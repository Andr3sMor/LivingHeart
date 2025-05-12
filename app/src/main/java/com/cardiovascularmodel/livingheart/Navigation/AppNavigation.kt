package com.cardiovascularmodel.livingheart.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.cardiovascularmodel.livingheart.Ui.Login.LoginScreen
import com.cardiovascularmodel.livingheart.Ui.Register.RegisterScreen
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
            LoginScreen()
        }
        composable (AppScreens.RegisterScreen.route){
            RegisterScreen()
        }
    }
}