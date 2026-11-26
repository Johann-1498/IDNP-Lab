package com.unsa.lab11.model

data class Recompensa(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val puntosRequeridos: Int,
    val descripcion: String,
    val disponible: Boolean = true,
    val porcentajeProgreso: Float = 1.0f
)
