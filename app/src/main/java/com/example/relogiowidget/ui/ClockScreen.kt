// ClockScreen.kt
package com.example.relogiowidget.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.res.stringResource
import com.example.relogiowidget.R

@Composable
fun ClockScreen(onScreenTap: () -> Unit) {
    val softRed = Color(0x66FF4444)
    var currentTime by remember { mutableStateOf("00:00") }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            delay(1000L)
        }
    }

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val newX = (-40..40).random().toFloat()
            val newY = (-40..40).random().toFloat()

            offsetX.animateTo(newX, tween(1000))
            offsetY.animateTo(newY, tween(1000))

            delay(15000L)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onScreenTap() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentTime,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            color = softRed,
            modifier = Modifier.offset(offsetX.value.dp, offsetY.value.dp)
        )
    }
}


