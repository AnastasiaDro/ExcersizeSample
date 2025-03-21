package com.cerebus.excersizesample.balls.presentation.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlin.random.Random

@Composable
fun StepOneScreen(
    viewModel: BallsViewModel,
    ballsUiItems: BallsUiItems = BallsUiItems(
        circleColor = Color.Yellow
    )
) {
    LaunchedEffect(Unit) {
        Log.d("qaz", "showBall")
        viewModel.updateStartTime(System.currentTimeMillis())
    }

    var isShowInfoForSharedPref by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    var isShow by remember { mutableStateOf(true) }
    var circleCenter by remember { mutableStateOf(Offset(0f, 0f)) }

    // Функция для генерации случайных координат
    fun generateRandomCenter(width: Float, height: Float): Offset {
        val randomX = ballsUiItems.circleRadius +
                (width - 2 * ballsUiItems.circleRadius) * Random.nextFloat()
        val randomY = ballsUiItems.circleRadius +
                (height - 2 * ballsUiItems.circleRadius) * Random.nextFloat()
        return Offset(randomX, randomY)
    }

    if (isShowInfoForSharedPref) {
        Box(modifier = Modifier.padding(40.dp)) {
            Column {
                Text("уровень сложности (0 - статика, 2 - движ) difficultLevel: ${state.lastGameParams.difficultLevel}")
                Text("Шаг сложности step: ${state.lastGameParams.step}")
                Text("ResultValue: ${state.lastGameParams.isGameSucceedStatus}")
                Text("время за сколько завершен уровень: ${state.lastGameParams.timeGameFinished}")
                Text("время, когда последний раз сыграли: ${state.lastGameParams.lastSucceedTime}")
            }
        }
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
                    if (distance <= ballsUiItems.circleRadius) {
                        isShow = false
                        viewModel.ballClicked()
                        isShowInfoForSharedPref = true
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
                    color = ballsUiItems.circleColor,
                    radius = ballsUiItems.circleRadius,
                    center = circleCenter
                )
                drawLine(
                    color = ballsUiItems.crossColor,
                    start = Offset(
                        circleCenter.x - ballsUiItems.crossLength / 2,
                        circleCenter.y - ballsUiItems.crossLength / 2
                    ),
                    end = Offset(
                        circleCenter.x + ballsUiItems.crossLength / 2,
                        circleCenter.y + ballsUiItems.crossLength / 2
                    ),
                    strokeWidth = ballsUiItems.crossThickness
                )
                drawLine(
                    color = ballsUiItems.crossColor,
                    start = Offset(
                        circleCenter.x - ballsUiItems.crossLength / 2,
                        circleCenter.y + ballsUiItems.crossLength / 2
                    ),
                    end = Offset(
                        circleCenter.x + ballsUiItems.crossLength / 2,
                        circleCenter.y - ballsUiItems.crossLength / 2
                    ),
                    strokeWidth = ballsUiItems.crossThickness
                )
            }
        }
    }
}

@Preview
@Composable
fun PrewSc() {
    StepOneScreen(
        viewModel = viewModel(),
        ballsUiItems = BallsUiItems(circleColor = Color.Red)
    )
}