package com.cardiovascularmodel.livingheart.Worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cardiovascularmodel.livingheart.Data.Fit.DailyHealthUploader

class DailyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val uploader = DailyHealthUploader(applicationContext)
        uploader.collectAndUploadDailyData()
        return Result.success()
    }
}
