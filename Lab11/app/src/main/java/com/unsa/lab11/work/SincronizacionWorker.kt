package com.unsa.lab11.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SincronizacionWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Simular sincronización en segundo plano (p. ej. subida de métricas de reciclaje)
            delay(2000L)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
