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
import kotlin.math.hypot

@Composable
fun StepZeroScreen(viewModel: BallsViewModel){
    val circleRadius = 200.0f
    val circleColor = Color.Red
    val crossColor = Color.Black
    val crossLength = circleRadius * 1f // Длина линий крестика
    val crossThickness = 10f // Толщина линий крестика

    var isShow by remember { mutableStateOf(true) }

    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    // Вычисляем центр экрана
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    // Вычисляем расстояние между точкой касания и центром круга
                    val distance = hypot(
                        tapOffset.x - centerX,
                        tapOffset.y - centerY
                    )
                    // Проверяем, находится ли точка касания внутри круга
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
                    center = Offset(size.width / 2, size.height / 2)
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
