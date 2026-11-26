package com.unsa.lab11.service

data class TimerState(
    val secondsElapsed: Long = 0,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false
) {
    fun formatTime(): String {
        val hours = secondsElapsed / 3600
        val minutes = (secondsElapsed % 3600) / 60
        val seconds = secondsElapsed % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
