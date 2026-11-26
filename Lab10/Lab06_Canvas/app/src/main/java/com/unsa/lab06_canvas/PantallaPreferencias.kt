package com.unsa.lab06_canvas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun PantallaPreferencias() {
    val context = LocalContext.current
    val repository = remember { UserPreferencesRepository(context) }
    val scope = rememberCoroutineScope()

    val darkMode by repository.darkModeFlow.collectAsStateWithLifecycle(initialValue = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Configuración de preferencias",
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Modo oscuro: ")
            Switch(
                checked = darkMode,
                onCheckedChange = { enabled ->
                    scope.launch { repository.setDarkMode(enabled) }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (darkMode) "Estado actual: modo oscuro activado"
                   else "Estado actual: modo claro",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
