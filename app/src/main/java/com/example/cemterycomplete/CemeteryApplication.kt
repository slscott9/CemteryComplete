package com.example.cemterycomplete

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cemterycomplete.worker.CemeteryRefreshWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CemeteryApplication  : Application()  , androidx.work.Configuration.Provider {

    val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()

    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
            Timber.d("running work")
        }
    }


    //Adds constraints for when work happens, sets up when work executes, and enqueues work if it has not finished
    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<CemeteryRefreshWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            CemeteryRefreshWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, //keep means if we have created this work before just keep it, do not create new work again
            repeatingRequest
        )
    }

    override fun getWorkManagerConfiguration(): androidx.work.Configuration =
        androidx.work.Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
}


