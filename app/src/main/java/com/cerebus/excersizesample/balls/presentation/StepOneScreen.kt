package com.cerebus.excersizesample.balls.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.nio.file.Files.size
import kotlin.math.hypot
import kotlin.random.Random
import kotlin.times

@Composable
fun StepOneScreen(viewModel: BallsViewModel) {
    val circleRadius = 200.0f
    val circleColor = Color.Yellow
    val crossColor = Color.Black
    val crossLength = circleRadius * 1f // Длина линий крестика
    val crossThickness = 10f // Толщина линий крестика

    ////    val uiState by viewModel.state.collectAsState()

    var isShow by remember { mutableStateOf(true) }
    var circleCenter by remember { mutableStateOf(Offset(0f, 0f)) }

    // Функция для генерации случайных координат
    fun generateRandomCenter(width: Float, height: Float): Offset {
        val randomX = circleRadius + (width - 2 * circleRadius) * Random.nextFloat()
        val randomY = circleRadius + (height - 2 * circleRadius) * Random.nextFloat()
        return Offset(randomX, randomY)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    // Проверяем, находится ли нажатие внутри круга
                    val distance = hypot(
                        tapOffset.x - circleCenter.x,
                        tapOffset.y - circleCenter.y
                    )
                    if (distance <= circleRadius) {
                        isShow = false
                        Log.d("qaz", "pressOne")
                    } else {
                        Log.d("qaz", "mimoOne")
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isShow) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width.toFloat()
                val height = size.height.toFloat()
                if (circleCenter == Offset(0f, 0f)) {
                    circleCenter = generateRandomCenter(width, height)
                }
                drawCircle(
                    color = circleColor,
                    radius = circleRadius,
                    center = circleCenter
                )
                drawLine(
                    color = crossColor,
                    start = Offset(circleCenter.x - crossLength / 2,
                        circleCenter.y - crossLength / 2),
                    end = Offset(circleCenter.x + crossLength / 2,
                        circleCenter.y + crossLength / 2),
                    strokeWidth = crossThickness
                )
                drawLine(
                    color = crossColor,
                    start = Offset(circleCenter.x - crossLength / 2,
                        circleCenter.y + crossLength / 2),
                    end = Offset(circleCenter.x + crossLength / 2,
                        circleCenter.y - crossLength / 2),
                    strokeWidth = crossThickness
                )
            }
        }
    }
}

@Preview
@Composable
fun PrewSc(){
    StepOneScreen(viewModel = viewModel())
}