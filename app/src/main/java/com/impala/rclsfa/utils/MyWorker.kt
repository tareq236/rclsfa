package com.impala.rclsfa.utils


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    var sessionManager: SessionManager = SessionManager(appContext)


    override fun doWork(): Result {
        Log.d("worker", "Sync Worker is running")
        GlobalScope.launch (Dispatchers.Main){
            val serviceIntent = Intent(appContext, MyBackgroundService::class.java)
            appContext.startService(serviceIntent)
        }

        return Result.success()
    }


}