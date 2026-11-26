package com.unsa.lab11

import com.unsa.lab11.service.TimerState
import org.junit.Assert.assertEquals
import org.junit.Test

class TimerServiceUnitTest {

    @Test
    fun timerState_formatTime_mm_ss() {
        val state = TimerState(secondsElapsed = 125, isRunning = true, isPaused = false)
        assertEquals("02:05", state.formatTime())
    }

    @Test
    fun timerState_formatTime_hh_mm_ss() {
        val state = TimerState(secondsElapsed = 3665, isRunning = true, isPaused = false)
        assertEquals("01:01:05", state.formatTime())
    }

    @Test
    fun timerState_initialValues() {
        val state = TimerState()
        assertEquals(0L, state.secondsElapsed)
        assertEquals(false, state.isRunning)
        assertEquals(false, state.isPaused)
        assertEquals("00:00", state.formatTime())
    }
}
