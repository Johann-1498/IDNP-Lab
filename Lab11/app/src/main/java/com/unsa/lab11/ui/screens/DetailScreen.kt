package com.unsa.lab11.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.lab11.data.RecompensaRepository
import com.unsa.lab11.ui.theme.UnsaGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    recompensaId: Int,
    onBackClick: () -> Unit
) {
    val repository = remember { RecompensaRepository() }
    val recompensa = remember(recompensaId) { repository.getRecompensaById(recompensaId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Recompensa") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        if (recompensa != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                Text(
                    text = recompensa.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = UnsaGreen.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = UnsaGreen)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${recompensa.puntosRequeridos} EcoPuntos",
                            fontWeight = FontWeight.Bold,
                            color = UnsaGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = recompensa.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* Canjear recompensa */ },
                    colors = ButtonDefaults.buttonColors(containerColor = UnsaGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Canjear Recompensa", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
