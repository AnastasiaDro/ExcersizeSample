package com.cerebus.excersizesample.balls.presentation.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cerebus.excersizesample.balls.presentation.BallsUiItems
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import kotlin.math.hypot

@Composable
fun StepZeroScreen(
    viewModel: BallsViewModel,
    ballsUiItems: BallsUiItems = BallsUiItems(circleColor = Color.Red)
) {
    var isShow by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        Log.d("qaz", "showBall")
        viewModel.updateStartTime(System.currentTimeMillis())
    }

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
                    if (distance <= ballsUiItems.circleRadius) {
                        isShow = false
                        viewModel.ballClicked()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isShow == true) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                drawCircle(
                    color = ballsUiItems.circleColor,
                    radius = ballsUiItems.circleRadius,
                    center = Offset(size.width / 2, size.height / 2)
                )
                drawLine(
                    color = ballsUiItems.crossColor,
                    start = Offset(
                        centerX - ballsUiItems.crossLength / 2,
                        centerY - ballsUiItems.crossLength / 2
                    ),
                    end = Offset(
                        centerX + ballsUiItems.crossLength / 2,
                        centerY + ballsUiItems.crossLength / 2
                    ),
                    strokeWidth = ballsUiItems.crossThickness
                )
                drawLine(
                    color = ballsUiItems.crossColor,
                    start = Offset(
                        centerX - ballsUiItems.crossLength / 2,
                        centerY + ballsUiItems.crossLength / 2
                    ),
                    end = Offset(
                        centerX + ballsUiItems.crossLength / 2,
                        centerY - ballsUiItems.crossLength / 2
                    ),
                    strokeWidth = ballsUiItems.crossThickness
                )
            }
        }
    }
}

