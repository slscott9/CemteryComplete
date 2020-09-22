package com.example.cemterycomplete.worker

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cemterycomplete.data.CemeteryRepository
import com.example.cemterycomplete.data.CemeteryRepositoryImpl
import com.example.cemterycomplete.data.entities.Cemetery
import timber.log.Timber
import java.lang.Exception

class CemeteryRefreshWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: CemeteryRepositoryImpl
) : CoroutineWorker(context, workerParameters) {

//Coroutine worker by default runs on Dispatchers.default

    companion object {
        const val WORK_NAME = "refresh_data_worker"
    }

    val testCemList = listOf<Cemetery>(
        Cemetery(
            cemeteryRowId = 1,
            cemeteryName = "Thorsby",
            cemeteryCounty = "Chilton",
            cemeteryState = "AL",
            cemeteryLocation = "Thorsby",
            firstYear = "1888",
            section = "T section",
            spot = "T spot",
            range = "T range",
            township = "T township"
        )
    )

    /*
    sendCemsToNetwork will call dao method to find new cemetery and graves that will be the list we send to network
 */
    override suspend fun doWork(): Result {
        Timber.i("Starting doWork")
        try {
            repository.sendNewCemeteriesToNetwork(testCemList)
            Timber.i("Work request for refreshing database is run")

            repository.refreshCemeteryList()
        } catch (e: Exception) {
            Timber.i( e.message.toString())

            return Result.retry()
        }
        return Result.success()
    }

}