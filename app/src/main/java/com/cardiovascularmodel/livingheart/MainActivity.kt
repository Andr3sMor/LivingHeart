package com.cardiovascularmodel.livingheart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.cardiovascularmodel.livingheart.Navigation.AppNavigation
import com.cardiovascularmodel.livingheart.Ui.PostRegister.GoogleFitViewModel
import com.cardiovascularmodel.livingheart.ui.theme.LivingHeartTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001
    }

    private val googleFitViewModel: GoogleFitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LivingHeartTheme {
                AppNavigation()
            }
        }
    }
}
