package com.example.lab07

/**
 * CUESTIONARIO DE CONSOLIDACIÓN - LAB 07
 *
 * 1. ¿Qué problema de rendimiento puede aparecer al usar Column con muchos elementos?
 *    - Column compone, mide y dibuja TODOS los elementos de la colección simultáneamente de manera ansiosa (eager),
 *      incluso aquellos que no están visibles en la pantalla. Cuando la lista es grande (ej. 20 o más registros),
 *      esto genera un alto consumo de memoria, retrasos en el primer renderizado (jank) y un uso excesivo de CPU
 *      al recalcular layouts innecesarios. LazyColumn soluciona esto componiendo solo los elementos visibles.
 *
 * 2. ¿Qué beneficio tiene separar el modelo de datos del composable visual?
 *    - Separa las preocupaciones (Separation of Concerns / Clean Architecture). El model (ej. ItemModel) representa
 *      la estructura pura de los datos independientes de la interfaz de usuario, facilitando pruebas unitarias,
 *      reutilización, persistencia en bases de datos (Room) o deserialización de APIs. El componente visual se encarga
 *      exclusivamente de renderizar la información de forma desacoplada y reutilizable (como en ItemCard).
 *
 * 3. ¿Para qué sirve identificar de forma estable los elementos de una lista?
 *    - Proporcionar claves estables (`key` en `items(items, key = { it.id })`) permite a LazyColumn realizar un seguimiento
 *      preciso de cada elemento cuando la colección cambia (reordenamiento, inserción o eliminación). Ayuda a que Compose
 *      reutilice correctamente el estado y las composiciones existentes en lugar de recomponer o redibujar elementos que
 *      no han cambiado, optimizando notablemente el rendimiento durante el desplazamiento y las actualizaciones.
 */
