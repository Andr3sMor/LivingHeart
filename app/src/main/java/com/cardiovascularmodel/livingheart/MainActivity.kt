package com.cardiovascularmodel.livingheart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cardiovascularmodel.livingheart.Navigation.AppNavigation
import com.cardiovascularmodel.livingheart.ui.theme.LivingHeartTheme

class MainActivity : ComponentActivity() {
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
