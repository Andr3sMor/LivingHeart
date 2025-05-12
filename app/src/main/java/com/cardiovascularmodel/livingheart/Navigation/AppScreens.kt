package com.cardiovascularmodel.livingheart.Navigation

sealed class AppScreens(val route: String){
    object SplashScreen: AppScreens("splash_screen")
    object MainsScreen: AppScreens("main_screen")
    object Login: AppScreens("login_screen")
}