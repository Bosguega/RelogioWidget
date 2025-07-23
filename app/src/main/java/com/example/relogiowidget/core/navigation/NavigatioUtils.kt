package com.example.relogiowidget.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

/**
 * Navega para uma rota, evitando múltiplas cópias da mesma tela na pilha.
 */
fun NavController.navigateSingleTopTo(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(route, navOptions {
        launchSingleTop = true
        restoreState = true
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
        builder()
    })
}

/**
 * Volta para a tela inicial da navegação.
 */
fun NavController.popToStart() {
    this.popBackStack(this.graph.startDestinationId, false)
}