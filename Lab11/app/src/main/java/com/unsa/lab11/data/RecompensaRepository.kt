package com.unsa.lab11.data

import com.unsa.lab11.model.Recompensa

class RecompensaRepository {

    private val recompensas = listOf(
        Recompensa(
            id = 1,
            nombre = "Cuaderno Ecológico Reciclado",
            categoria = "Útiles",
            puntosRequeridos = 150,
            descripcion = "Cuaderno A5 elaborado 100% con papel reciclado por la comunidad UNSA."
        ),
        Recompensa(
            id = 2,
            nombre = "Tomatodo Termo UNSA",
            categoria = "Accesorios",
            puntosRequeridos = 300,
            descripcion = "Tomatodo térmico de acero inoxidable de 750ml con el logo de UNSA Recicla."
        ),
        Recompensa(
            id = 3,
            nombre = "Bolsa Reutilizable de Algodón",
            categoria = "Accesorios",
            puntosRequeridos = 100,
            descripcion = "Bolsa de tela orgánica ultra resistente para compras ecológicas."
        ),
        Recompensa(
            id = 4,
            nombre = "Planta Suculenta en Maceta Biodegradable",
            categoria = "Ecológicos",
            puntosRequeridos = 80,
            descripcion = "Pequeña suculenta cultivada en el vivero universitario UNSA."
        ),
        Recompensa(
            id = 5,
            nombre = "Descuento en Comedor Universitario (50%)",
            categoria = "Beneficios",
            puntosRequeridos = 200,
            descripcion = "Vale por 50% de descuento en tu menú universitario diario."
        )
    )

    fun getRecompensas(): List<Recompensa> = recompensas

    fun getRecompensaById(id: Int): Recompensa? = recompensas.find { it.id == id }
}
