package com.cerebus.excersizesample.balls.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.hypot
import kotlin.random.Random

@Composable
fun StepThreeScreen(viewModel: BallsViewModel) {
    val circleRadius = 200.0f
    val circleColor = Color.Red
    val crossColor = Color.Black
    val crossLength = circleRadius * 1f // Длина линий крестика
    val crossThickness = 10f // Толщина линий крестика

    var isShow by remember { mutableStateOf(true) }

    var circleCenter by remember { mutableStateOf(Offset(0f, 0f)) }

    // Функция для генерации случайных координат
    fun generateRandomCenter(width: Float, height: Float): Offset {
        val randomX = circleRadius + (width - 2 * circleRadius) * Random.nextFloat()
        val randomY = circleRadius + (height - 2 * circleRadius) * Random.nextFloat()
        return Offset(randomX, randomY)
    }

    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val distance = hypot(
                        tapOffset.x - centerX,
                        tapOffset.y - centerY
                    )
                    if (distance <= circleRadius) {
                        isShow = false
                        Log.d("qaz", "press")
                    } else {
                        Log.d("qaz", "mimo")
                    }
                }
            },
        contentAlignment = Alignment.Center
    ){
        if (isShow == true){
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                drawCircle(
                    color = circleColor,
                    radius = circleRadius,
                    center = generateRandomCenter(size.width.toFloat(), size.height.toFloat())
                )
                drawLine(
                    color = crossColor,
                    start = Offset(centerX - crossLength / 2, centerY - crossLength / 2),
                    end = Offset(centerX + crossLength / 2, centerY + crossLength / 2),
                    strokeWidth = crossThickness
                )
                drawLine(
                    color = crossColor,
                    start = Offset(centerX - crossLength / 2, centerY + crossLength / 2),
                    end = Offset(centerX + crossLength / 2, centerY - crossLength / 2),
                    strokeWidth = crossThickness
                )
            }
        }
    }
}

@Preview
@Composable
fun Prew(){
    StepThreeScreen(viewModel = viewModel())
}