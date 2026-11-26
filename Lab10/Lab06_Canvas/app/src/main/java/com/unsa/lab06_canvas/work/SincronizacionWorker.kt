package com.unsa.lab06_canvas.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.unsa.lab06_canvas.UserPreferencesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Tarea diferible de UNSA RECICLA: "sincronización" de EcoPuntos.
 *
 * En este prototipo no hay servidor: el trabajo se simula con una espera de 3 s
 * y deja un resultado observable (log, marca de tiempo y contador en DataStore).
 * Corre en un hilo de trabajo (CoroutineWorker), nunca en el hilo principal.
 */
class SincronizacionWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val origen = inputData.getString(KEY_ORIGEN) ?: "desconocido"
        Log.d(TAG, "INICIO origen=$origen intento=$runAttemptCount hilo=${Thread.currentThread().name}")

        return try {
            delay(DURACION_SIMULADA_MS) // simula consulta/sincronización con un servidor

            val ahora = System.currentTimeMillis()
            val total = UserPreferencesRepository(applicationContext).registrarSincronizacion(ahora)

            Log.d(TAG, "FIN OK origen=$origen total=$total marca=$ahora")
            Result.success(workDataOf(KEY_TIMESTAMP to ahora, KEY_TOTAL to total))
        } catch (e: CancellationException) {
            Log.w(TAG, "CANCELADO origen=$origen")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "ERROR origen=$origen: ${e.message}")
            Result.retry()
        }
    }

    companion object {
        const val TAG = "UnsaWorker"
        const val KEY_ORIGEN = "origen"
        const val KEY_TIMESTAMP = "timestamp"
        const val KEY_TOTAL = "total"
        private const val DURACION_SIMULADA_MS = 3_000L
    }
}
