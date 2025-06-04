package com.cardiovascularmodel.livingheart.Ui.PostRegister

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoogleFitViewModel : ViewModel() {

    companion object {
        const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001
    }

    val fitnessOptions: FitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    fun setConnected(connected: Boolean) {
        _isConnected.value = connected
    }

    fun isPermissionApproved(activity: Activity): Boolean {
        val account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions)
        return GoogleSignIn.hasPermissions(account, fitnessOptions)
    }

    fun requestPermissionsIfNeeded(activity: Activity, requestCode: Int) {
        if (!isPermissionApproved(activity)) {
            val account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions)
            GoogleSignIn.requestPermissions(
                activity,
                requestCode,
                account,
                fitnessOptions
            )
        } else {
            setConnected(true)
        }
    }
}
