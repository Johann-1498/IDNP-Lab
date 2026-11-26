package com.unsa.lab06_canvas.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkInfo
import com.unsa.lab06_canvas.UserPreferencesRepository
import com.unsa.lab06_canvas.ui.theme.UnsaGreen
import com.unsa.lab06_canvas.work.SincronizacionScheduler
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun textoEstado(info: WorkInfo?): String = when (info?.state) {
    null -> "Sin programar"
    WorkInfo.State.ENQUEUED -> "En cola (esperando retardo o restricciones)"
    WorkInfo.State.RUNNING -> "Ejecutando en segundo plano…"
    WorkInfo.State.SUCCEEDED -> "Completada"
    WorkInfo.State.FAILED -> "Falló"
    WorkInfo.State.CANCELLED -> "Cancelada"
    WorkInfo.State.BLOCKED -> "Bloqueada"
}

@Composable
fun SincronizacionCard() {
    val context = LocalContext.current
    val repository = remember { UserPreferencesRepository(context) }

    val unica by remember { SincronizacionScheduler.observarUnica(context) }
        .collectAsStateWithLifecycle(initialValue = null)
    val periodica by remember { SincronizacionScheduler.observarPeriodica(context) }
        .collectAsStateWithLifecycle(initialValue = null)
    val ultima by repository.ultimaSincronizacionFlow.collectAsStateWithLifecycle(initialValue = 0L)
    val total by repository.totalSincronizacionesFlow.collectAsStateWithLifecycle(initialValue = 0)

    val formato = remember { SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("es", "PE")) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Sync, contentDescription = null, tint = UnsaGreen)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Sincronización en segundo plano",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "WorkManager · requiere conexión a internet",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))

            Text("Tarea única: ${textoEstado(unica)}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            if (unica?.state == WorkInfo.State.RUNNING) {
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = UnsaGreen)
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "Tarea periódica (15 min): ${textoEstado(periodica)}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (ultima == 0L) "Última sincronización: nunca"
                else "Última sincronización: ${formato.format(Date(ultima))} (total: $total)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { SincronizacionScheduler.programarUnica(context, retardoSegundos = 10) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = UnsaGreen)
            ) { Text("Sincronizar en 10 s") }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = { SincronizacionScheduler.programarPeriodica(context) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Programar cada 15 min") }

            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = { SincronizacionScheduler.cancelarTodo(context) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cancelar tareas programadas") }
        }
    }
}
