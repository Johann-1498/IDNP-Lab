package com.unsa.lab06_canvas.model

data class Recompensa(
    val id: Int,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String,
    val costoPuntos: Int,
    val categoria: String,
    val pasos: List<String>,
    val terminos: List<String>
)
