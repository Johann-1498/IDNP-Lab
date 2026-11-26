package com.unsa.lab11.work

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

object SincronizacionScheduler {

    private const val WORK_TAG = "sincronizacion_reciclaje_work"

    fun programarSincronizacionUnica(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SincronizacionWorker>()
            .setConstraints(constraints)
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_TAG,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun obtenerEstadoSincronizacion(context: Context): Flow<List<WorkInfo>> {
        return WorkManager.getInstance(context).getWorkInfosByTagFlow(WORK_TAG)
    }
}
