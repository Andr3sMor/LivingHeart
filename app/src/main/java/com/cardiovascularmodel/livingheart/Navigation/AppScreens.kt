package com.cardiovascularmodel.livingheart.Navigation

sealed class AppScreens(val route: String){
    object SplashScreen: AppScreens("splash_screen")
    object Login: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
}