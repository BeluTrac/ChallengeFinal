package com.belutrac.challengefinal.api

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.belutrac.challengefinal.database.getDatabase
import com.belutrac.challengefinal.main.MainRepository

class SyncWorkManager (appContext: Context, params : WorkerParameters): CoroutineWorker(appContext,params){
    companion object{
        const val WORK_NAME = "SyncWorkManager"
    }

    private val repository = MainRepository(appContext as Application)
    override suspend fun doWork(): Result {
        repository.fetchTeams()
        return Result.success()
    }

}