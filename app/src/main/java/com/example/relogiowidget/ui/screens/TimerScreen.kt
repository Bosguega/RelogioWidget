package com.example.relogiowidget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalDensity

@Composable
fun TimerScreen(
    onBack: () -> Unit = {}
) {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    var seconds by remember { mutableStateOf(0) }
    var repeatSound by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botão de voltar
        TextButton(onClick = onBack) {
            Text("← Voltar")
        }

        Text(
            text = "Timer Regressivo",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TimePicker(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            onTimeChanged = { h, m, s ->
                hours = h
                minutes = m
                seconds = s
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Repetir som")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = repeatSound,
                onCheckedChange = { repeatSound = it }
            )
        }

        Button(
            onClick = {
                // TODO: Iniciar timer regressivo aqui
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Iniciar")
        }
    }
}

@Composable
fun TimePicker(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onTimeChanged: (h: Int, m: Int, s: Int) -> Unit
) {
    val hourRange = (0..23).toList()
    val minuteSecondRange = (0..59).toList()

    Row(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WheelPicker(
            items = hourRange,
            selected = hours,
            label = "Horas",
            onSelectedChange = { onTimeChanged(it, minutes, seconds) }
        )
        WheelPicker(
            items = minuteSecondRange,
            selected = minutes,
            label = "Minutos",
            onSelectedChange = { onTimeChanged(hours, it, seconds) }
        )
        WheelPicker(
            items = minuteSecondRange,
            selected = seconds,
            label = "Segundos",
            onSelectedChange = { onTimeChanged(hours, minutes, it) }
        )
    }
}

@Composable
fun WheelPicker(
    items: List<Int>,
    selected: Int,
    label: String,
    onSelectedChange: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // 🔁 Repete a lista várias vezes para simular ciclo infinito
    val repeatCount = 5
    val repeatedItems = remember(items) {
        List(repeatCount) { items }.flatten()
    }

    // 📏 Parâmetros visuais
    val itemHeight = 40.dp
    val visibleItemsCount = 3
    val centerOffset = visibleItemsCount / 2

    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }

    // 🎯 Posição inicial centralizada
    val startIndex = items.size * (repeatCount / 2) + items.indexOf(selected)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val flingBehavior = rememberSnapFlingBehavior(listState)

    // 🧠 Calcula o índice central visível com offset real
    val centerIndex by remember {
        derivedStateOf {
            val offset = listState.firstVisibleItemScrollOffset
            val index = listState.firstVisibleItemIndex
            if (offset < itemHeightPx / 2f) index + centerOffset else index + centerOffset + 1
        }
    }

    // 🚨 Corrige seleção e reposiciona no centro da lista
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val realIndex = centerIndex % items.size
            onSelectedChange(items[realIndex])

            val buffer = items.size
            if (centerIndex < buffer || centerIndex > repeatedItems.size - buffer) {
                coroutineScope.launch {
                    val targetIndex = items.size * (repeatCount / 2) + realIndex
                    listState.scrollToItem(targetIndex - centerOffset)
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .height(itemHeight * visibleItemsCount)
                .width(70.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = itemHeight)
            ) {
                items(repeatedItems.size) { index ->
                    val value = repeatedItems[index]
                    val isSelected = index == centerIndex

                    Text(
                        text = value.toString().padStart(2, '0'),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        modifier = Modifier
                            .height(itemHeight)
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index - centerOffset)
                                }
                            }
                    )
                }
            }

            // 🔹 Linha indicadora (opcional)
            Box(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            )
        }
    }
}




