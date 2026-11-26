# Laboratorio 11: Servicio en Primer Plano y Notificación Persistente con Temporizador

**Asignatura:** Introducción al Desarrollo de Nuevas Plataformas (IDNP)  
**Escuela Profesional de Ingeniería de Sistemas - UNSA**  
**Año Lectivo:** 2026-B  

---

## 📌 Descripción del Proyecto

El **Laboratorio 11** implementa un **Servicio en Primer Plano (Foreground Service)** en Android integrado con la aplicación **UNSA Recicla**. Este servicio permite a los estudiantes de la UNSA cronometrar en tiempo real la duración de sus sesiones de recolección de residuos ecológicos y reciclaje. 

Mientras el temporizador está activo, el servicio mantiene una **Notificación Persistente e Imborrable** en la barra de estado del sistema Android, permitiendo visualizar el tiempo transcurrido (formato `mm:ss` / `hh:mm:ss`) e interactuar mediante acciones rápidas de **Pausar**, **Reanudar** y **Detener** directamente desde la notificación o desde la interfaz en Jetpack Compose.

---

## 🛠️ Componentes Clave e Implementación Técnica

1. **`TimerForegroundService` (`com.unsa.lab11.service`)**:
   - Hereda de `Service()` y administra la ejecución del temporizador en un hilo secundario mediante Kotlin Coroutines (`CoroutineScope` con `Dispatchers.Default`).
   - Crea un `NotificationChannel` con prioridad baja (`IMPORTANCE_LOW`) para actualizar el tiempo en la notificación sin emitir sonidos continuos cada segundo.
   - Llama a `startForeground(NOTIFICATION_ID, notification)` configurando `foregroundServiceType="specialUse"`.
   - Expone un `StateFlow<TimerState>` estático que transmite el estado en tiempo real (`secondsElapsed`, `isRunning`, `isPaused`) hacia las vistas en Jetpack Compose.

2. **Acciones Rápidas en la Notificación Persistente**:
   - `ACTION_START`: Inicia el servicio y la corrutina del temporizador.
   - `ACTION_PAUSE`: Pausa el tiempo y actualiza la notificación a `[PAUSADO]`.
   - `ACTION_RESUME`: Reanuda el conteo de tiempo.
   - `ACTION_STOP`: Detiene el servicio, invoca `stopForeground(STOP_FOREGROUND_REMOVE)` y elimina la notificación.

3. **Interfaz de Usuario con Jetpack Compose y Canvas**:
   - **`TimerScreen`**: Pantalla principal con badge de estado, botones interactivos y tarjetas informativas.
   - **`CronometroCanvas`**: Componente personalizado que dibuja en tiempo real un reloj circular con graduación de marcas de minutos, un arco verde fluido con degradado y un efecto de pulso central animado.
   - **Permisos Dinámicos**: Solicita el permiso `POST_NOTIFICATIONS` en Android 13+ (API 33+) mediante `rememberLauncherForActivityResult`.

4. **Configuración en `AndroidManifest.xml`**:
   - `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />`
   - `<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />`
   - `<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />`
   - Declaración explícita del servicio con `foregroundServiceType="specialUse"` y propiedad `PROPERTY_SPECIAL_USE_FGS_SUBTYPE`.

---

## 📝 Cuestionario de Consolidación (Sección VII)

### 1. ¿Cuál es la diferencia entre una notificación local y una notificación push?
- **Notificación Local:** Se crea, programa e inicia internamente dentro del propio dispositivo Android a través de código ejecutado por la aplicación (por ejemplo, mediante `NotificationManager` desde un `Foreground Service` o un `AlarmManager`). No requiere conexión a servidores ni acceso a Internet.
- **Notificación Push:** Es originada desde un servidor remoto externo y enviada al dispositivo móvil a través de un servicio de mensajería en la nube (como Firebase Cloud Messaging - FCM o Apple Push Notification service). Se utiliza para informar al usuario sobre eventos remotos (como mensajes entrantes, actualizaciones de servidor o promociones).

### 2. ¿Por qué un servicio en primer plano requiere informar al usuario mediante una notificación?
Un **Servicio en Primer Plano** realiza tareas de ejecución continua que consumen recursos significativos del dispositivo (como procesador, memoria RAM y batería), incluso cuando la aplicación está minimizada o el usuario está en otra app. Por razones de **seguridad, privacidad y transparencia**, el sistema operativo Android exige obligatoriamente mostrar una notificación persistente e imborrable mientras el servicio se encuentra activo. De este modo, el usuario siempre está consciente de qué aplicación está consumiendo recursos en segundo plano y dispone de los medios para detenerla.

### 3. ¿En qué situación WorkManager sería preferible a un servicio en primer plano?
**WorkManager** es preferible cuando la tarea a realizar es **diferible o postergable (deferrable)**, no requiere una respuesta o visualización inmediata en tiempo real por parte del usuario, y debe garantizarse su ejecución incluso si la aplicación se cierra o se reinicia el dispositivo. Además, WorkManager permite asociar restricciones del sistema (por ejemplo, ejecutar solo cuando el dispositivo esté conectado a una red Wi-Fi no medida o esté cargando batería). En contraste, un Servicio en Primer Plano es necesario únicamente cuando la tarea requiere una **ejecución inmediata e ininterrumpida que el usuario debe percibir activamente** (como reproducción de audio, navegación GPS o un cronómetro en vivo).

---

## ✅ Checklist de Preparación para Publicación

- [x] **Nombre de la Aplicación:** UNSA Recicla - Lab11
- [x] **Versión:** 1.0 (versionCode = 1, versionName = "1.0")
- [x] **Target SDK:** API 35 / **Compile SDK:** API 35 / **Min SDK:** API 24
- [x] **Iconos:** Adaptive Icons (`ic_launcher`, `ic_launcher_round`) y Vector Icon (`ic_timer`).
- [x] **Pruebas Unitarias:** Clase de prueba `TimerServiceUnitTest` implementada y ejecutada exitosamente.
- [x] **Estructura:** Carpeta independiente `Lab11/` dentro del repositorio GitHub.
