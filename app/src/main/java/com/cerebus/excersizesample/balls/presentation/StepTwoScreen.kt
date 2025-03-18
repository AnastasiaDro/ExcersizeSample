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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.hypot
import kotlin.random.Random

@Composable
fun StepTwoScreen(viewModel: BallsViewModel) {
    val circleRadius = 200.0f
//    val circleColor = Color.Yellow
    val crossColor = Color.Black
    val crossLength = circleRadius * 1f // Длина линий крестика
    val crossThickness = 10f // Толщина линий крестика

    var isBall1Visible by remember { mutableStateOf(true) }
    var isBall2Visible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isBall1Visible) {
            Ball(
                radius = circleRadius,
                color = Color.Green,
                crossColor = crossColor,
                crossLength = crossLength,
                crossThickness = crossThickness,
                onClose = { isBall1Visible = false },
                number = "1",
            )
        }

        if (isBall2Visible) {
            Ball(
                radius = circleRadius,
                color = Color.Blue,
                crossColor = crossColor,
                crossLength = crossLength,
                crossThickness = crossThickness,
                onClose = { isBall2Visible = false },
                number = "2",
            )
        }
    }
}

@Composable
fun Ball(
    radius: Float,
    color: Color,
    crossColor: Color,
    crossLength: Float,
    crossThickness: Float,
    onClose: () -> Unit , // Колбэк для закрытия шарика
    number: String,
) {
    var center by remember { mutableStateOf(Offset(0f, 0f)) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    // Проверяем, находится ли нажатие внутри круга
                    val distance = hypot(
                        tapOffset.x - center.x,
                        tapOffset.y - center.y
                    )
                    if (distance <= radius) {
                        onClose() // Закрываем шарик

//                        isShow = false
                        Log.d("qaz", "pressTwo $number")
                    } else {
                        Log.d("qaz", "mimoTwo $number")
                    }
                }


//                detectTapGestures { tapOffset ->
//                    // Проверяем, находится ли нажатие внутри крестика
//                    val crossStartX = center.x - crossLength / 2
//                    val crossEndX = center.x + crossLength / 2
//                    val crossStartY = center.y - crossLength / 2
//                    val crossEndY = center.y + crossLength / 2
//
//                    if (tapOffset.x in crossStartX..crossEndX && tapOffset.y in crossStartY..crossEndY) {
//                        Log.d()
//                        onClose() // Закрываем шарик
//                    }
//                }
            }
    ) {
        val width = size.width.toFloat()
        val height = size.height.toFloat()
        if (center == Offset(0f, 0f)) {
            center = Offset(
                radius + (width - 2 * radius) * Random.nextFloat(),
                radius + (height - 2 * radius) * Random.nextFloat()
            )
        }
        // Рисуем круг
        drawCircle(
            color = color,
            radius = radius,
            center = center
        )
        // Рисуем крестик
        drawLine(
            color = crossColor,
            start = Offset(center.x - crossLength / 2, center.y - crossLength / 2),
            end = Offset(center.x + crossLength / 2, center.y + crossLength / 2),
            strokeWidth = crossThickness
        )
        drawLine(
            color = crossColor,
            start = Offset(center.x - crossLength / 2, center.y + crossLength / 2),
            end = Offset(center.x + crossLength / 2, center.y - crossLength / 2),
            strokeWidth = crossThickness
        )
    }
}


@Preview
@Composable
fun Prew(){
    StepTwoScreen(viewModel = viewModel())
}