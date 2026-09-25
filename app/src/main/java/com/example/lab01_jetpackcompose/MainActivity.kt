package com.example.lab01_jetpackcompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab01_jetpackcompose.ui.theme.Lab01_JetpackComposeTheme

// Color verde oscuro personalizado para el tema de UNSA RECICLA (#2E7D32)
val VerdeOscuroUNSA = Color(0xFF2E7D32)
val VerdeClaroUNSA = Color(0xFFE8F5E9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab01_JetpackComposeTheme {
                AppNavegacion()
            }
        }
    }
}

/**
 * Sistema de navegación principal de la aplicación utilizando NavHost y NavController.
 */
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pantalla_inicio"
    ) {
        // Pantalla 1: Inicio / Registro
        composable("pantalla_inicio") {
            PantallaInicio(
                onIngresar = { nombreCui ->
                    val paramCodificado = Uri.encode(nombreCui)
                    navController.navigate("pantalla_destino/$paramCodificado")
                }
            )
        }

        // Pantalla 2: Destino(con parámetro de navegación)
        composable(
            route = "pantalla_destino/{nombreCui}",
            arguments = listOf(
                navArgument("nombreCui") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nombreCui = backStackEntry.arguments?.getString("nombreCui") ?: "Estudiante"
            PantallaDestino(
                nombreCui = nombreCui,
                navController = navController
            )
        }
    }
}

/**
 * Pantalla 1: PantallaInicio
 * Muestra el título UNSA RECICLA, formulario de ingreso (Nombre/CUI) y botón de validación.
 */
@Composable
fun PantallaInicio(onIngresar: (String) -> Unit) {
    // Estados locales para gestionar el texto ingresado y los mensajes de error
    var textoIngresado by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono decorativo de Reciclaje
            Icon(
                imageVector = Icons.Default.Recycling,
                contentDescription = "Logo UNSA Recicla",
                tint = VerdeOscuroUNSA,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título principal en verde oscuro #2E7D32
            Text(
                text = "UNSA RECICLA",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeOscuroUNSA,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Recicla tus botellas PET y gana EcoPuntos",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Input para Nombre o CUI
            OutlinedTextField(
                value = textoIngresado,
                onValueChange = { nuevoTexto ->
                    textoIngresado = nuevoTexto
                    // Si el usuario empieza a escribir, limpiamos el error
                    if (mostrarError && nuevoTexto.isNotBlank()) {
                        mostrarError = false
                        mensajeError = ""
                    }
                },
                label = { Text("Nombre o CUI del estudiante") },
                isError = mostrarError,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Mensaje de validación en texto rojo si está vacío o tiene solo espacios
            if (mostrarError) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Ingresar con validación
            Button(
                onClick = {
                    if (textoIngresado.isBlank()) {
                        mostrarError = true
                        mensajeError = "Por favor, ingrese su Nombre o CUI para continuar."
                    } else {
                        mostrarError = false
                        onIngresar(textoIngresado.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeOscuroUNSA),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ingresar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Pantalla 2: PantallaDestino
 * Muestra el TopAppBar con botón de regreso (navController.popBackStack())
 * y la bienvenida al estudiante con sus EcoPuntos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDestino(
    nombreCui: String,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UNSA RECICLA", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeOscuroUNSA
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Tarjeta de bienvenida y EcoPuntos
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = VerdeClaroUNSA
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Recycling,
                        contentDescription = "EcoPuntos",
                        tint = VerdeOscuroUNSA,
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¡Hola, $nombreCui!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeOscuroUNSA,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tienes 120 EcoPuntos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1B5E20),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¡Sigue reciclando en el campus para canjear recompensas!",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    Lab01_JetpackComposeTheme {
        PantallaInicio(onIngresar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaDestinoPreview() {
    Lab01_JetpackComposeTheme {
        PantallaDestino(
            nombreCui = "Estudiante UNSA",
            navController = rememberNavController()
        )
    }
}
