package com.unsa.lab11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unsa.lab11.data.UserPreferencesRepository
import com.unsa.lab11.ui.screens.*
import com.unsa.lab11.ui.theme.Lab11Theme
import com.unsa.lab11.ui.theme.UnsaGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val repository = remember { UserPreferencesRepository(context) }
            val darkMode by repository.darkModeFlow.collectAsStateWithLifecycle(initialValue = false)

            Lab11Theme(darkTheme = darkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnsaReciclaApp()
                }
            }
        }
    }
}

@Composable
fun UnsaReciclaApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("timer", "catalog", "impact", "profile")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = UnsaGreen
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Timer, contentDescription = "Temporizador") },
                        label = { Text("Temporizador") },
                        selected = currentRoute == "timer",
                        onClick = {
                            if (currentRoute != "timer") {
                                navController.navigate("timer") {
                                    popUpTo("timer") { inclusive = true }
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = UnsaGreen,
                            selectedTextColor = UnsaGreen,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Catálogo") },
                        selected = currentRoute == "catalog",
                        onClick = {
                            if (currentRoute != "catalog") {
                                navController.navigate("catalog") {
                                    popUpTo("timer")
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = UnsaGreen,
                            selectedTextColor = UnsaGreen,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Eco, contentDescription = "Mi Impacto") },
                        label = { Text("Mi Impacto") },
                        selected = currentRoute == "impact",
                        onClick = {
                            if (currentRoute != "impact") {
                                navController.navigate("impact") {
                                    popUpTo("timer")
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = UnsaGreen,
                            selectedTextColor = UnsaGreen,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Perfil") },
                        label = { Text("Perfil") },
                        selected = currentRoute == "profile",
                        onClick = {
                            if (currentRoute != "profile") {
                                navController.navigate("profile") {
                                    popUpTo("timer")
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = UnsaGreen,
                            selectedTextColor = UnsaGreen,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "timer",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("timer") {
                TimerScreen()
            }
            composable("catalog") {
                CatalogScreen(
                    onRecompensaClick = { recompensaId ->
                        navController.navigate("detail/$recompensaId")
                    },
                    onProfileClick = {
                        navController.navigate("profile")
                    }
                )
            }
            composable(
                route = "detail/{recompensaId}",
                arguments = listOf(navArgument("recompensaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recompensaId = backStackEntry.arguments?.getInt("recompensaId") ?: 1
                DetailScreen(
                    recompensaId = recompensaId,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("impact") {
                ImpactScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
