package com.example.relogiowidget.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.WorldClocks.route) {

        composable(NavRoutes.WorldClocks.route) {
            WorldClocksScreen(
                onClockTap = {
                    // Se quiser abrir tela cheia (ex: ClockActivity)
                    // Ou fazer algo especial
                }
            )
        }

        composable(NavRoutes.Alarms.route) {
            AlarmsScreen()
        }

        composable(NavRoutes.Timer.route) {
            TimerScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.Stopwatch.route) {
            StopwatchScreen()
        }

        // Se quiser incluir Welcome ou Clock:
        // composable(NavRoutes.Welcome.route) { WelcomeScreen() }
        // composable(NavRoutes.Clock.route) { ClockScreen() }
    }
}
