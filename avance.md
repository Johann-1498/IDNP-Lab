Actúa como un Desarrollador Android Senior experto en Jetpack Compose y Material Design 3. 

Tenemos un proyecto existente llamado "Lab06_Canvas" (package: com.unsa.lab06_canvas) donde ya implementamos:
1. Un Canvas animado (IndicadorAnimado) para mostrar progreso.
2. Persistencia de datos con DataStore (UserPreferencesRepository) que guarda si el usuario prefiere "Modo Oscuro" (dark_mode).

El objetivo ahora es transformar esta aplicación en un prototipo funcional completo llamado "UNSA RECICLA". La app es un catálogo de recompensas e impacto ambiental para estudiantes de la Universidad Nacional de San Agustín (UNSA).

Basado en el diseño visual proporcionado (3 pantallas: Catálogo, Detalle y Perfil), necesito que implementes lo siguiente:

REQUISITOS DE DISEÑO Y UI (Material 3):
- Colores principales: Verde oscuro (UNSA) para la barra superior (TopAppBar) y botones principales. Fondo gris muy claro (casi blanco) en modo claro.
- Tipografía limpia, uso de Cards con esquinas redondeadas (rounded corners) y elevación suave.
- Integrar el Modo Oscuro: La app debe leer el `dark_mode` de DataStore al iniciar (en el MainActivity o Theme) para aplicar el ColorScheme oscuro o claro automáticamente.

REQUISITOS DE NAVEGACIÓN Y PANTALLAS:
Implementa un NavHost (usando Jetpack Compose Navigation) con 4 destinos principales conectados por una Barra de Navegación Inferior (NavigationBar):
1. Pantalla "Inicio" (Catálogo): 
   - TopAppBar con el título "UNSA RECICLA" y un ícono de perfil a la derecha.
   - Un header de bienvenida ("¡Hola, Agustín!").
   - Una Card destacada de "Mi Impacto" con estadísticas simples (ej. 12 Botellas, 0.54 kg CO2).
   - Un buscador.
   - Un LazyColumn con Cards de recompensas (ej. Descuento Comedor, Transferencia Yape, Wi-Fi Prioritario, etc.). Cada Card debe tener ícono, título, descripción, costo en "EcoPuntos" y un botón/flecha para ir al detalle.
2. Pantalla "Detalle de Recompensa":
   - Recibe el ID o nombre de la recompensa por parámetro de navegación.
   - Muestra un banner superior con la información.
   - Pasos para acreditar el beneficio (con íconos de check).
   - Términos y condiciones.
   - Botón fijo en la parte inferior "Confirmar y Canjear".
3. Pantalla "Mi Impacto":
   - Muestra estadísticas detalladas del reciclaje del usuario.
   - Usa el componente `IndicadorAnimado` (el Canvas que creamos en la práctica 06) para mostrar visualmente el nivel de progreso hacia la siguiente meta de reciclaje.
4. Pantalla "Perfil / Configuración":
   - Muestra los datos del usuario.
   - Incluye el Switch de "Modo Oscuro" (conectado a DataStore).
   - Historial de canjes (puede ser una lista simple estática por ahora).

ESTRUCTURA DEL PROYECTO:
- Crea los archivos necesarios (pantallas, componentes reutilizables como `RecompensaCard`, `NavBar`).
- Usa datos estáticos (mocks) en una clase `RecompensaRepository` para poblar la lista de recompensas y no complicar la arquitectura con Room o APIs externas todavía.
- Asegúrate de mantener el código limpio y separado por responsabilidades.

Por favor, genera el código paso a paso. Primero dame la configuración del Theme (para soportar el modo oscuro de DataStore), luego los datos mock, después las pantallas y finalmente el NavHost. Si necesitas crear múltiples archivos, indícame claramente la ruta y el nombre de cada uno.