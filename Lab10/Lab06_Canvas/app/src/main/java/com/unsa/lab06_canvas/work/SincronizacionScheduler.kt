package com.unsa.lab06_canvas.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

/** Punto único para programar, observar y cancelar la sincronización. */
object SincronizacionScheduler {

    const val TRABAJO_UNICO = "sync_unico"
    const val TRABAJO_PERIODICO = "sync_periodico"

    // Una sincronización de datos solo tiene sentido con conexión a internet.
    private val conRed = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    // La periódica, además, evita ejecutarse con batería baja.
    private val conRedYBateria = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    /** Prueba rápida: trabajo único con retardo (útil porque el mínimo periódico es 15 min). */
    fun programarUnica(context: Context, retardoSegundos: Long = 10) {
        val request = OneTimeWorkRequestBuilder<SincronizacionWorker>()
            .setInitialDelay(retardoSegundos, TimeUnit.SECONDS)
            .setConstraints(conRed)
            .setInputData(workDataOf(SincronizacionWorker.KEY_ORIGEN to "unica"))
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(TRABAJO_UNICO, ExistingWorkPolicy.REPLACE, request)
    }

    /** Extensión: Android exige un intervalo mínimo de 15 minutos para trabajo periódico. */
    fun programarPeriodica(context: Context) {
        val request = PeriodicWorkRequestBuilder<SincronizacionWorker>(15, TimeUnit.MINUTES)
            .setConstraints(conRedYBateria)
            .setInputData(workDataOf(SincronizacionWorker.KEY_ORIGEN to "periodica"))
            .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(TRABAJO_PERIODICO, ExistingPeriodicWorkPolicy.UPDATE, request)
    }

    fun cancelarTodo(context: Context) {
        val wm = WorkManager.getInstance(context)
        wm.cancelUniqueWork(TRABAJO_UNICO)
        wm.cancelUniqueWork(TRABAJO_PERIODICO)
    }

    fun observarUnica(context: Context): Flow<WorkInfo?> =
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkFlow(TRABAJO_UNICO)
            .map { it.firstOrNull() }

    fun observarPeriodica(context: Context): Flow<WorkInfo?> =
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkFlow(TRABAJO_PERIODICO)
            .map { it.firstOrNull() }
}
