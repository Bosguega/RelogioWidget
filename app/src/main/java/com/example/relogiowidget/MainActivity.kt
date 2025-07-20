// MainActivity.kt
package com.example.relogiowidget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Seus próprios arquivos
import com.example.relogiowidget.ui.NavigationGraph
import com.example.relogiowidget.ui.NavRoutes
import com.example.relogiowidget.ui.BottomBar



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.WorldClocks.route

    Column(modifier = Modifier.fillMaxSize()) {
        NavigationGraph(navController = navController)
        BottomBar(currentRoute = currentRoute, onItemSelected = { item ->
            navController.navigate(item.route) {
                // Evita múltiplas cópias da mesma tela na pilha
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
            }
        })
    }
}
