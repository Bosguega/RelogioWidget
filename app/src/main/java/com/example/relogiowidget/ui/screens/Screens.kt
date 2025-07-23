// Screens.kt
package com.example.relogiowidget.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

@Composable
fun WorldClocksScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Relógios Mundiais", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun AlarmsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Alarmes", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun TimerScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Timer", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun StopwatchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cronômetro", style = MaterialTheme.typography.headlineMedium)
    }
}
