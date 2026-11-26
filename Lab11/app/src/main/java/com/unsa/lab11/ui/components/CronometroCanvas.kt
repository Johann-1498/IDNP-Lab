package com.unsa.lab11.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsa.lab11.ui.theme.UnsaGreen
import com.unsa.lab11.ui.theme.UnsaGreenLight
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CronometroCanvas(
    secondsElapsed: Long,
    isRunning: Boolean,
    isPaused: Boolean,
    modifier: Modifier = Modifier.size(260.dp)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val sweepAngle = ((secondsElapsed % 60) / 60f) * 360f

    val hours = secondsElapsed / 3600
    val minutes = (secondsElapsed % 3600) / 60
    val seconds = secondsElapsed % 60
    val formattedTime = if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = (size.minDimension / 2) - 20.dp.toPx()

            // Fondo circular exterior con marcas de minutos
            drawCircle(
                color = trackColor.copy(alpha = 0.4f),
                radius = radius,
                center = center
            )

            for (i in 0 until 60) {
                val angleRad = Math.toRadians((i * 6).toDouble())
                val isMajor = i % 5 == 0
                val tickLength = if (isMajor) 12.dp.toPx() else 6.dp.toPx()
                val tickColor = if (isMajor) primaryColor else trackColor

                val startX = center.x + (radius - tickLength) * cos(angleRad).toFloat()
                val startY = center.y + (radius - tickLength) * sin(angleRad).toFloat()
                val endX = center.x + radius * cos(angleRad).toFloat()
                val endY = center.y + radius * sin(angleRad).toFloat()

                drawLine(
                    color = tickColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = if (isMajor) 3.dp.toPx() else 1.5f.dp.toPx()
                )
            }

            // Anillo de progreso circular
            val strokeWidth = 14.dp.toPx()
            val arcSize = Size(radius * 2, radius * 2)
            val topLeft = Offset(center.x - radius, center.y - radius)

            // Pista de fondo
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )

            // Arco activo con degradado
            if (secondsElapsed > 0) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            UnsaGreenLight,
                            UnsaGreen,
                            UnsaGreenLight
                        ),
                        center = center
                    ),
                    startAngle = -90f,
                    sweepAngle = if (sweepAngle == 0f && secondsElapsed > 0) 360f else sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            // Indicador de pulso central si está activo
            if (isRunning && !isPaused) {
                val currentPulse = radius * 0.15f * pulseScale
                drawCircle(
                    color = UnsaGreen.copy(alpha = 0.25f),
                    radius = currentPulse,
                    center = center
                )
            }
        }

        // Texto digital en el centro
        Text(
            text = formattedTime,
            fontSize = if (hours > 0) 32.sp else 38.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPaused) Color.DarkGray else MaterialTheme.colorScheme.onBackground
        )
    }
}
