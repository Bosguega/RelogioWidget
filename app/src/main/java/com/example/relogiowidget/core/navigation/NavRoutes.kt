package com.example.relogiowidget.core.navigation

import com.example.relogiowidget.R

sealed class NavRoutes(
    val route: String,
    val label: String,
    val iconResId: Int
) {
    object Welcome : NavRoutes("welcome", "Início", R.drawable.ic_home)
    object WorldClocks : NavRoutes("worldClocks", "Relógios", R.drawable.ic_clock)
    object Alarms : NavRoutes("alarms", "Alarmes", R.drawable.ic_alarm)
    object Timer : NavRoutes("timer", "Timer", R.drawable.ic_timer)
    object Stopwatch : NavRoutes("stopwatch", "Cronômetro", R.drawable.ic_stopwatch)
    object Clock : NavRoutes("clock", "Relógio", R.drawable.ic_clock) // opcional, depende do uso

    companion object {
        fun fromRoute(route: String?): NavRoutes? {
            return when (route) {
                Welcome.route -> Welcome
                WorldClocks.route -> WorldClocks
                Alarms.route -> Alarms
                Timer.route -> Timer
                Stopwatch.route -> Stopwatch
                Clock.route -> Clock
                else -> null
            }
        }
    }
}