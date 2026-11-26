Actúa como un Desarrollador Android Senior experto en Jetpack Compose y Material Design 3. 

Tenemos un proyecto existente (package: com.unsa.lab06_canvas) donde ya implementamos el estado y persistencia para el "Modo Oscuro" usando DataStore.

El objetivo es transformar esta app en un prototipo funcional completo llamado "UNSA RECICLA", basándonos ESTRICTAMENTE en el diseño visual de referencia que se describe a continuación.

=== DISEÑO VISUAL Y UI (Material 3) ===
- Paleta de colores: 
  - Verde principal: #1B7A36 (para botones, TopAppBar y acentos).
  - Verde claro: #E8F5E9 (para fondos de tarjetas destacadas).
  - Fondo general: #F8F9FA (gris muy claro).
  - Texto principal: #333333, Texto secundario: #757575.
- Componentes UI:
  - Usa `TopAppBar` con el logo/título "UNSA RECICLA" alineado a la izquierda y un ícono de perfil a la derecha.
  - Usa `NavigationBar` (barra inferior) con 3 ítems: "Inicio" (Home), "Canjear" (ShoppingBag), "Mi Impacto" (Leaf/Eco).
  - Las tarjetas (`Card`) deben tener esquinas redondeadas (16.dp), borde sutil (Color #E0E0E0) y fondo blanco.
  - Usa íconos de Material Design (ej. `Icons.Default.Home`, `Icons.Default.ShoppingCart`, `Icons.Default.Eco`).

=== ESTRUCTURA DE PANTALLAS (Basado en la imagen) ===

1. PANTALLA "CATÁLOGO" (Inicio):
   - Header de saludo: "¡Hola, Agustín!" + un chip verde claro que diga "💡 120 EcoPuntos".
   - Tarjeta de estado: "Tu perfil de ciudadano: 120 pts acumulados" con un ícono de medalla.
   - Barra de búsqueda (TextField con ícono de lupa y filtro).
   - Filtros horizontales: "Todos (01)", "Comedor", "Yape / Plin" (Textos en un Row con scroll).
   - Lista vertical (`LazyColumn`) de recompensas. Cada tarjeta de recompensa debe tener: 
     - Ícono a la izquierda (en un fondo de color).
     - Título (ej. "Descuento Comedor Universitario").
     - Subtítulo (ej. "Válido para menú completo en...").
     - Precio a la derecha: un chip con "10 Pts" y el ícono de una hoja/eco.
   - Texto al final: "Cada tarjeta contribuye a la economía circular...".

2. PANTALLA "DETALLE DE RECOMPENSA":
   - TopAppBar con botón "Atrás" (flecha).
   - Card grande con gradiente azul verdoso: "TRANSFERENCIA EXPRESS" (ejemplo).
   - Título de la recompensa: "Transferencia S/ 1.00 a Billetera Digital".
   - Badge de precio: "⚡ 20 EcoPuntos".
   - Sección "¿Cómo se acredita el beneficio?" con 3 pasos numerados (círculos con números) e íconos.
   - Sección "Términos y Condiciones" con checkmarks verdes.
   - BottomBar fijo: Muestra "Tu saldo: 120 Pts" y un botón verde grande "Confirmar y Canjear (20 Pts)".

3. PANTALLA "MI IMPACTO":
   - Header: "¡Hola, Agustín!" y eco-puntos.
   - Card verde destacada: "Identifícate en la RVM" con un botón blanco "Mostrar Mi QR RVM".
   - Card "Tu Impacto Ambiental": Muestra estadísticas (12 Botellas, 0.54 kg CO2 mitigado) con íconos.
   - AQUÍ INTEGRA EL CÓDIGO: Usa el `IndicadorAnimado` (nuestro Canvas del Lab 06) para mostrar el progreso visual del reciclaje, por ejemplo, un anillo o círculo que se llena del 60%. Ponle el texto "Progreso a Nivel 3".
   - Sección "Canjes Sugeridos" con tarjetas horizontales.

4. PANTALLA "PERFIL / CONFIGURACIÓN" (Accesible desde el ícono de perfil del TopAppBar):
   - Muestra el avatar del usuario, nombre y correo.
   - AQUÍ INTEGRA EL CÓDIGO: Un Switch que diga "Modo Oscuro" conectado a nuestro `UserPreferencesRepository` y a DataStore.
   - Botón de "Cerrar Sesión" (rojo).

=== LÓGICA Y DATOS ===
- Usa Jetpack Compose Navigation (`NavHost` y `NavController`).
- Crea una clase de datos `Recompensa` y un objeto `MockData` para llenar las listas con los datos que se ven en la imagen (Descuento Comedor, Transferencia Yape, 10 Impresiones, etc.).
- Asegúrate de que el cambio de Modo Oscuro se aplique a toda la app (Theme).

Por favor, empieza generando la estructura de paquetes, las clases de datos (Mock) y el Theme (Colores y Tipografía). Luego procede con cada pantalla una por una.