package com.unsa.lab06_canvas.data

import com.unsa.lab06_canvas.model.Recompensa

object RecompensaRepository {
    val listaRecompensas = listOf(
        Recompensa(
            id = 1,
            titulo = "Descuento Comedor Universitario",
            subtitulo = "Válido para menú completo en comedor central",
            descripcion = "Obtén un descuento especial del 50% en tu menú diario presentando el código QR generado en el comedor universitario de la UNSA.",
            costoPuntos = 10,
            categoria = "Comedor",
            pasos = listOf(
                "Canjea la recompensa en la app UNSA RECICLA.",
                "Acércate a caja del comedor central.",
                "Muestra tu código QR de validación al personal."
            ),
            terminos = listOf(
                "Válido de lunes a viernes en horario de almuerzo.",
                "Un canje por día por estudiante.",
                "Sujeto a disponibilidad del comedor."
            )
        ),
        Recompensa(
            id = 2,
            titulo = "Transferencia S/ 1.00 a Billetera Digital",
            subtitulo = "Abono directo a tu Yape o Plin registrado",
            descripcion = "Canjea tus EcoPuntos por dinero real transferido directamente a tu billetera digital asociada a tu número de celular UNSA.",
            costoPuntos = 20,
            categoria = "Yape / Plin",
            pasos = listOf(
                "Confirma tu número de celular vinculado en tu perfil.",
                "Selecciona la opción de transferencia express.",
                "Recibe el abono en un plazo máximo de 24 horas hábiles."
            ),
            terminos = listOf(
                "Requiere tener cuenta activa en Yape o Plin.",
                "Máximo 3 transferencias por semana.",
                "Aplica únicamente para estudiantes matriculados."
            )
        ),
        Recompensa(
            id = 3,
            titulo = "10 Impresiones en Biblioteca Central",
            subtitulo = "Páginas B/N en laboratorios de biblioteca",
            descripcion = "Imprime tus trabajos de investigación y lecturas obligatorias sin costo en los módulos habilitados de la biblioteca central.",
            costoPuntos = 15,
            categoria = "Biblioteca",
            pasos = listOf(
                "Canjea tus puntos para obtener el voucher digital.",
                "Acércate al tótem de impresión en Biblioteca Central.",
                "Escanea tu código de impresión."
            ),
            terminos = listOf(
                "Válido solo para impresiones en blanco y negro.",
                "Vigencia de 30 días a partir del canje.",
                "No acumulable con otras promociones."
            )
        ),
        Recompensa(
            id = 4,
            titulo = "Wi-Fi Prioritario Campus UNSA",
            subtitulo = "Acceso a red de alta velocidad por 1 semana",
            descripcion = "Navega sin restricciones ni límites de ancho de banda en todas las facultades conectadas a la red inalámbrica UNSA-PRO.",
            costoPuntos = 25,
            categoria = "Conectividad",
            pasos = listOf(
                "Canjea el beneficio en la app.",
                "El acceso se habilitará automáticamente en tu usuario institucional.",
                "Conéctate a la red UNSA-PRO desde tus dispositivos."
            ),
            terminos = listOf(
                "Válido por 7 días calendario.",
                "Aplica para laptops y teléfonos registrados.",
                "Uso exclusivo para fines académicos y de investigación."
            )
        )
    )

    fun obtenerPorId(id: Int): Recompensa? {
        return listaRecompensas.find { it.id == id }
    }
}
