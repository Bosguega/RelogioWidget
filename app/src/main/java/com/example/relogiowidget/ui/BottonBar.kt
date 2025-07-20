// BottomBar.kt
package com.example.relogiowidget.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    currentRoute: String,
    onItemSelected: (NavRoutes) -> Unit
) {
    val items = listOf(
        NavRoutes.WorldClocks,
        NavRoutes.Alarms,
        NavRoutes.Timer,
        NavRoutes.Stopwatch
    )

    NavigationBar {
        items.forEach { item ->
            val isSelected = item.route == currentRoute
            val scale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1f)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                icon = {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp).scale(scale)
                        )
                        Text(
                            text = item.label,
                            modifier = Modifier.scale(scale),
                            style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                        )
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}
