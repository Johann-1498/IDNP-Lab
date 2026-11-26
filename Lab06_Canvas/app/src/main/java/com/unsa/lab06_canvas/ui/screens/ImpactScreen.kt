package com.unsa.lab06_canvas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsa.lab06_canvas.IndicadorAnimado
import com.unsa.lab06_canvas.ui.theme.UnsaGreen
import com.unsa.lab06_canvas.ui.theme.UnsaGreenLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpactScreen() {
    var showQrDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Impacto Ambiental", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = UnsaGreen)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card RVM QR
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UnsaGreen)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Identifícate en la RVM",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Escanea tu QR en las máquinas recicladoras del campus",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showQrDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.QrCode, contentDescription = null, tint = UnsaGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Mostrar Mi QR RVM", color = UnsaGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estadísticas ambientales
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Estadísticas Acumuladas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem(valor = "12", label = "Botellas recicladas", unidad = "unidades")
                        StatItem(valor = "0.54 kg", label = "CO₂ mitigado", unidad = "gases")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Integración del IndicadorAnimado (Canvas Lab 06)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Progreso a Nivel 3",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Visualización interactiva de tu meta",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Canvas animado del laboratorio 06
                    IndicadorAnimado()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showQrDialog) {
        AlertDialog(
            onDismissRequest = { showQrDialog = false },
            title = { Text("Código QR RVM - UNSA") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .background(UnsaGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = "QR RVM",
                            tint = UnsaGreen,
                            modifier = Modifier.size(140.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Código: UNSA-AGUSTIN-2026", fontWeight = FontWeight.Bold)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showQrDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = UnsaGreen)
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun StatItem(valor: String, label: String, unidad: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = UnsaGreenLight
        ) {
            Text(
                text = valor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = UnsaGreen
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
        Text(text = unidad, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
