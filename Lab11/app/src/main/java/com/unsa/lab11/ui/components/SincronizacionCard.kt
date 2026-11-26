package com.unsa.lab11.ui.components

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
import androidx.work.WorkInfo
import com.unsa.lab11.ui.theme.UnsaGreen
import com.unsa.lab11.work.SincronizacionScheduler

@Composable
fun SincronizacionCard() {
    val context = LocalContext.current
    val workInfoState by SincronizacionScheduler.obtenerEstadoSincronizacion(context)
        .collectAsState(initial = emptyList())

    val workInfo = workInfoState.firstOrNull()
    val estadoText = when (workInfo?.state) {
        WorkInfo.State.ENQUEUED -> "Programado"
        WorkInfo.State.RUNNING -> "En ejecución..."
        WorkInfo.State.SUCCEEDED -> "Completado con éxito"
        WorkInfo.State.FAILED -> "Falló la sincronización"
        WorkInfo.State.CANCELLED -> "Cancelado"
        else -> "Sin programar"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = null,
                    tint = UnsaGreen
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sincronización en Segundo Plano (WorkManager)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Estado actual: $estadoText",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { SincronizacionScheduler.programarSincronizacionUnica(context) },
                colors = ButtonDefaults.buttonColors(containerColor = UnsaGreen)
            ) {
                Text("Ejecutar Sincronización Ahora")
            }
        }
    }
}
