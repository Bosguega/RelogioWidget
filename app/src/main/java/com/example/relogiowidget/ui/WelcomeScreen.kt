package com.example.relogiowidget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.relogiowidget.R

@Composable
fun WelcomeScreen(
    onStartClockClicked: () -> Unit,
    onStartTimerClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onStartClockClicked) {
                Text(
                    text = stringResource(id = R.string.open_clock),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Button(onClick = onStartTimerClicked) {
                Text(
                    text = stringResource(id = R.string.open_timer),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}
