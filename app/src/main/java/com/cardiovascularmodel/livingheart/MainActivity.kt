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
import com.google.android.gms.auth.api.signin.GoogleSignIn

class MainActivity : ComponentActivity() {

    companion object {
        const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001
    }

    private val googleFitViewModel: GoogleFitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionsLauncher = {
            val fitnessOptions = googleFitViewModel.getFitnessOptions()
            val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

            if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    account,
                    fitnessOptions
                )
            } else {
                googleFitViewModel.setConnected(true)
            }
        }

        enableEdgeToEdge()
        setContent {
            LivingHeartTheme {
                AppNavigation(requestPermissionsLauncher)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE && resultCode == RESULT_OK) {
            googleFitViewModel.setConnected(true)
        }
    }
}
