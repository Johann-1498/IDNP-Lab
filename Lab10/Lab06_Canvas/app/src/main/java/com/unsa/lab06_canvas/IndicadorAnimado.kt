package com.unsa.lab06_canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IndicadorAnimado(modifier: Modifier = Modifier) {
    // Estado: controla el radio objetivo (rememberSaveable evita perderlo al rotar)
    var radioObjetivo by rememberSaveable { mutableStateOf(60f) }

    // Animación: transición suave del radio objetivo al valor animado
    val radioAnimado by animateFloatAsState(
        targetValue = radioObjetivo,
        animationSpec = tween(durationMillis = 600),
        label = "radioAnimado"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Área del Canvas: 200x200 dp con fondo gris para verlo mejor
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFF0F0F0))
        ) {
            val centroX = size.width / 2
            val centroY = size.height / 2

            // Dibuja el círculo, usando el radio animado
            drawCircle(
                color = Color(0xFF2196F3),
                radius = radioAnimado,
                center = Offset(centroX, centroY)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de estado: cambia según el radio objetivo
        Text(
            text = if (radioObjetivo == 60f) "Estado: Reposo" else "Estado: Activo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Texto de progreso en porcentaje
        Text(
            text = "Progreso: ${((radioObjetivo - 60f) / 40f * 100).toInt()}%",
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón que alterna el radio objetivo
        Button(onClick = {
            radioObjetivo = if (radioObjetivo == 60f) 100f else 60f
        }) {
            Text("Cambiar tamaño")
        }
    }
}