package com.belutrac.challengefinal.api

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkerUtil{

    fun scheduleSync(context: Context){
        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest =
            PeriodicWorkRequestBuilder<SyncWorkManager>(1, TimeUnit.DAYS)
            .setConstraints(constrains)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP , syncRequest)
    }
}