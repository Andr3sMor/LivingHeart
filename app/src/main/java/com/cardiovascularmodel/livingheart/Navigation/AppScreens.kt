package com.cardiovascularmodel.livingheart.Navigation

sealed class AppScreens(val route: String){
    object SplashScreen: AppScreens("splash_screen")
    object Login: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object DashboardScreen: AppScreens("dashboard_screen")
    object RiskAssessmentScreen: AppScreens("Risk_Screen")

    /*Post Login-Register*/
    object GoogleFitScreen: AppScreens("google_fit_screen")
    object PerfilUsuarioScreen: AppScreens("perfil_usuario_screen")
}