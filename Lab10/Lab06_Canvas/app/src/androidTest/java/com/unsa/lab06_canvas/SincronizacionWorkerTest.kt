package com.unsa.lab06_canvas

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.unsa.lab06_canvas.work.SincronizacionWorker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SincronizacionWorkerTest {

    @Test
    fun worker_devuelve_success_y_guarda_marca_de_tiempo() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = UserPreferencesRepository(context)
        val antes = repo.totalSincronizacionesFlow.first()

        val worker = TestListenableWorkerBuilder<SincronizacionWorker>(context)
            .setInputData(workDataOf(SincronizacionWorker.KEY_ORIGEN to "prueba"))
            .build()
        val resultado = worker.doWork()

        assertTrue(resultado is ListenableWorker.Result.Success)
        assertEquals(antes + 1, repo.totalSincronizacionesFlow.first())
        assertTrue(repo.ultimaSincronizacionFlow.first() > 0L)
    }
}
