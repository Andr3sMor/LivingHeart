package com.cardiovascularmodel.livingheart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cardiovascularmodel.livingheart.Navigation.AppNavigation
import com.cardiovascularmodel.livingheart.Ui.PostRegister.GoogleFitViewModel
import com.cardiovascularmodel.livingheart.Util.calculateDelayUntil23h59
import com.cardiovascularmodel.livingheart.Worker.DailyWorker
import com.cardiovascularmodel.livingheart.ui.theme.LivingHeartTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val googleFitViewModel: GoogleFitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateDelayUntil23h59(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "dailyHealthUploader",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyWorkRequest
        )

        enableEdgeToEdge()
        setContent {
            LivingHeartTheme {
                AppNavigation(googleFitViewModel)  // Pasa el viewModel a tu navegación
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GoogleFitViewModel.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            val connected = GoogleSignIn.hasPermissions(
                GoogleSignIn.getAccountForExtension(this, googleFitViewModel.fitnessOptions),
                googleFitViewModel.fitnessOptions
            )
            googleFitViewModel.setConnected(connected)
        }
    }
}

