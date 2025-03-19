package com.cerebus.excersizesample.balls.presentation.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import kotlin.math.hypot
import kotlin.random.Random
//
//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//    val circleColor = Color.Yellow
//    val crossColor = Color.Black
//    val crossLength = circleRadius * 1f // Длина линий крестика
//    val crossThickness = 10f // Толщина линий крестика
//
//    var isBall1Visible by remember { mutableStateOf(true) }
//    var isBall2Visible by remember { mutableStateOf(true) }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                crossColor = crossColor,
//                crossLength = crossLength,
//                crossThickness = crossThickness,
//                onClose = { isBall1Visible = false },
//                number = "1",
//            )
//        }
//
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                crossColor = crossColor,
//                crossLength = crossLength,
//                crossThickness = crossThickness,
//                onClose = { isBall2Visible = false },
//                number = "2",
//            )
//        }
//    }
//}
//
//@Composable
//fun Ball(
//    radius: Float,
//    color: Color,
//    crossColor: Color,
//    crossLength: Float,
//    crossThickness: Float,
//    onClose: () -> Unit , // Колбэк для закрытия шарика
//    number: String,
//) {
//    var center by remember { mutableStateOf(Offset(0f, 0f)) }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    // Проверяем, находится ли нажатие внутри круга
//                    val distance = hypot(
//                        tapOffset.x - center.x,
//                        tapOffset.y - center.y
//                    )
//                    if (distance <= radius) {
//                        onClose() // Закрываем шарик
//
////                        isShow = false
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//        if (center == Offset(0f, 0f)) {
//            center = Offset(
//                radius + (width - 2 * radius) * Random.nextFloat(),
//                radius + (height - 2 * radius) * Random.nextFloat()
//            )
//        }
//        // Рисуем круг
//        drawCircle(
//            color = color,
//            radius = radius,
//            center = center
//        )
//        // Рисуем крестик
//        drawLine(
//            color = crossColor,
//            start = Offset(center.x - crossLength / 2, center.y - crossLength / 2),
//            end = Offset(center.x + crossLength / 2, center.y + crossLength / 2),
//            strokeWidth = crossThickness
//        )
//        drawLine(
//            color = crossColor,
//            start = Offset(center.x - crossLength / 2, center.y + crossLength / 2),
//            end = Offset(center.x + crossLength / 2, center.y - crossLength / 2),
//            strokeWidth = crossThickness
//        )
//    }
//}


//@Preview
//@Composable
//fun Prew(){
//    StepTwoScreen(viewModel = viewModel())
//}
//
//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//
//    var isBall1Visible by remember { mutableStateOf(true) }
//    var isBall2Visible by remember { mutableStateOf(true) }
//
//    // Состояния для хранения центров шариков
//    var ball1Center by remember { mutableStateOf(Offset(0f, 0f)) }
//    var ball2Center by remember { mutableStateOf(Offset(0f, 0f)) }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                onClose = { isBall1Visible = false },
//                number = "1",
//                center = ball1Center,
//                onCenterGenerated = { newCenter ->
//                    ball1Center = newCenter
//                },
//                existingCenters = listOf(ball2Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                onClose = { isBall2Visible = false },
//                number = "2",
//                center = ball2Center,
//                onCenterGenerated = { newCenter ->
//                    ball2Center = newCenter
//                },
//                existingCenters = listOf(ball1Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//    }
//}
//
//@Composable
//fun Ball(
//    radius: Float,
//    color: Color,
//    onClose: () -> Unit, // Колбэк для закрытия шарика
//    number: String,
//    center: Offset,
//    onCenterGenerated: (Offset) -> Unit, // Колбэк для передачи сгенерированного центра
//    existingCenters: List<Offset> // Список центров других шариков
//) {
//    var localCenter by remember { mutableStateOf(center) }
//
//    // Обновляем localCenter при изменении center
//    LaunchedEffect(center) {
//        localCenter = center
//    }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    // Проверяем, находится ли нажатие внутри круга
//                    val distance = hypot(
//                        tapOffset.x - localCenter.x,
//                        tapOffset.y - localCenter.y
//                    )
//                    if (distance <= radius) {
//                        onClose() // Закрываем шарик
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//
//        // Генерация нового центра, если он еще не задан
//        if (localCenter == Offset(0f, 0f)) {
//            var newCenter: Offset
//            do {
//                newCenter = Offset(
//                    radius + (width - 2 * radius) * Random.nextFloat(),
//                    radius + (height - 2 * radius) * Random.nextFloat()
//                )
//            } while (isOverlapping(newCenter, existingCenters, radius)) // Проверяем пересечение
//
//            localCenter = newCenter
//            onCenterGenerated(newCenter) // Передаем новый центр в родительский компонент
//        }
//
//        // Рисуем круг
//        drawCircle(
//            color = color,
//            radius = radius,
//            center = localCenter
//        )
//    }
//}
//
//// Функция для проверки пересечения шариков
//fun isOverlapping(newCenter: Offset, existingCenters: List<Offset>, circleRadius : Float): Boolean {
//    return existingCenters.any { existingCenter ->
//        hypot(newCenter.x - existingCenter.x, newCenter.y - existingCenter.y) <= 2 * circleRadius
//    }
//}

//
//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//
//    var isBall1Visible by remember { mutableStateOf(true) }
//    var isBall2Visible by remember { mutableStateOf(true) }
//
//    // Состояния для хранения центров шариков
//    var ball1Center by remember { mutableStateOf(Offset(0f, 0f)) }
//    var ball2Center by remember { mutableStateOf(Offset(0f, 0f)) }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                onClose = { isBall1Visible = false },
//                number = "1",
//                center = ball1Center,
//                onCenterGenerated = { newCenter ->
//                    ball1Center = newCenter
//                },
//                existingCenters = listOf(ball2Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                onClose = { isBall2Visible = false },
//                number = "2",
//                center = ball2Center,
//                onCenterGenerated = { newCenter ->
//                    ball2Center = newCenter
//                },
//                existingCenters = listOf(ball1Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//    }
//}
//
//@Composable
//fun Ball(
//    radius: Float,
//    color: Color,
//    onClose: () -> Unit, // Колбэк для закрытия шарика
//    number: String,
//    center: Offset,
//    onCenterGenerated: (Offset) -> Unit, // Колбэк для передачи сгенерированного центра
//    existingCenters: List<Offset> // Список центров других шариков
//) {
//    var localCenter by remember { mutableStateOf(center) }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    // Проверяем, находится ли нажатие внутри круга
//                    val distance = hypot(
//                        tapOffset.x - localCenter.x,
//                        tapOffset.y - localCenter.y
//                    )
//                    if (distance <= radius) {
//                        onClose() // Закрываем шарик
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//
//        // Генерация нового центра, если он еще не задан
//        if (localCenter == Offset(0f, 0f)) {
//            var newCenter: Offset
//            do {
//                newCenter = Offset(
//                    radius + (width - 2 * radius) * Random.nextFloat(),
//                    radius + (height - 2 * radius) * Random.nextFloat()
//                )
//            } while (isOverlapping(newCenter, existingCenters, radius)) // Проверяем пересечение
//
//            localCenter = newCenter
//            onCenterGenerated(newCenter) // Передаем новый центр в родительский компонент
//        }
//
//        // Рисуем круг
//        drawCircle(
//            color = color,
//            radius = radius,
//            center = localCenter
//        )
//    }
//}
//
//// Функция для проверки пересечения шариков
//fun isOverlapping(newCenter: Offset, existingCenters: List<Offset>, circleRadius : Float): Boolean {
//    return existingCenters.any { existingCenter ->
//        hypot(newCenter.x - existingCenter.x, newCenter.y - existingCenter.y) <= 2 * circleRadius
//    }
//}



//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//    var isBall1Visible by remember { mutableStateOf(true) }
//    var isBall2Visible by remember { mutableStateOf(true) }
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                onClose = { isBall1Visible = false },
//                number = "1",
//            )
//        }
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                onClose = { isBall2Visible = false },
//                number = "2",
//            )
//        }
//    }
//}
//
//@Composable
//fun Ball(radius: Float, color: Color, onClose: () -> Unit, number: String) {
//    var center by remember { mutableStateOf(Offset(0f, 0f)) }
//    Canvas(
//        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
//            detectTapGestures { tapOffset ->
//                // Изменения здесь для проверки нажатия в любом порядке
//                if (center != Offset(0f, 0f)) {
//                    val distance = hypot(tapOffset.x - center.x, tapOffset.y - center.y)
//                    if (distance <= radius) {
//                        onClose()
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//        }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//        if (center == Offset(0f, 0f)) {
//            center = Offset(radius + (width - 2 * radius) * Random.nextFloat(),
//                radius + (height - 2 * radius) * Random.nextFloat())
//        }
//        drawCircle(color = color, radius = radius, center = center)
//    }
//}



//
//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//    var isBall1Visible by remember { mutableStateOf(true) }
//    var isBall2Visible by remember { mutableStateOf(true) }
//    Box(modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center)
//    {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                onClose = { isBall1Visible = false },
//                number = "1",
//            )
//        }
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                onClose = { isBall2Visible = false },
//                number = "2",
//            )
//        }
//    }
//}
//@Composable
//fun Ball(radius: Float, color: Color,
//    onClose: () -> Unit ,
//    number: String,
//) {
//    var center by remember { mutableStateOf(Offset(0f, 0f)) }
//    Canvas(modifier = Modifier.fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    val distance = hypot(tapOffset.x - center.x,
//                        tapOffset.y - center.y
//                    )
//                    if (distance <= radius) { onClose()
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//        if (center == Offset(0f, 0f)) { center = Offset(radius + (width - 2 * radius) * Random.nextFloat(),
//                radius + (height - 2 * radius) * Random.nextFloat()) }
//        drawCircle(color = color, radius = radius, center = center)
//    }
//}
//
//@Composable
//fun StepTwoScreen(viewModel: BallsViewModel) {
//    val circleRadius = 200.0f
//
//    val isBall1Visible by viewModel.isBall1Visible.collectAsState()
//    val isBall2Visible by viewModel.isBall2Visible.collectAsState()
//
//    val ball1Center by viewModel.ball1Center.collectAsState()
//    val ball2Center by viewModel.ball2Center.collectAsState()
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isBall1Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Green,
//                onClose = { viewModel.hideBall1() },
//                number = "1",
//                center = ball1Center,
//                onCenterGenerated = { newCenter ->
//                    viewModel.updateBall1Center(newCenter)
//                },
//                existingCenters = listOf(ball2Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//
//        if (isBall2Visible) {
//            Ball(
//                radius = circleRadius,
//                color = Color.Blue,
//                onClose = { viewModel.hideBall2() },
//                number = "2",
//                center = ball2Center,
//                onCenterGenerated = { newCenter ->
//                    viewModel.updateBall2Center(newCenter)
//                },
//                existingCenters = listOf(ball1Center).filter { it != Offset(0f, 0f) }
//            )
//        }
//    }
//}
//
//@Composable
//fun Ball(
//    radius: Float,
//    color: Color,
//    onClose: () -> Unit, // Колбэк для закрытия шарика
//    number: String,
//    center: Offset,
//    onCenterGenerated: (Offset) -> Unit, // Колбэк для передачи сгенерированного центра
//    existingCenters: List<Offset> // Список центров других шариков
//) {
//    // Используем center напрямую, без localCenter
//    val currentCenter = center
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    // Проверяем, находится ли нажатие внутри круга
//                    val distance = hypot(
//                        tapOffset.x - currentCenter.x,
//                        tapOffset.y - currentCenter.y
//                    )
//                    if (distance <= radius) {
//                        onClose() // Закрываем шарик
//                        Log.d("qaz", "pressTwo $number")
//                    } else {
//                        Log.d("qaz", "mimoTwo $number")
//                    }
//                }
//            }
//    ) {
//        val width = size.width.toFloat()
//        val height = size.height.toFloat()
//
//        // Генерация нового центра, если он еще не задан
//        if (currentCenter == Offset(0f, 0f)) {
//            var newCenter: Offset
//            do {
//                newCenter = Offset(
//                    radius + (width - 2 * radius) * Random.nextFloat(),
//                    radius + (height - 2 * radius) * Random.nextFloat()
//                )
//            } while (isOverlapping(newCenter, existingCenters, radius)) // Проверяем пересечение
//
//            onCenterGenerated(newCenter) // Передаем новый центр в родительский компонент
//        }
//
//        // Рисуем круг
//        drawCircle(
//            color = color,
//            radius = radius,
//            center = currentCenter
//        )
//    }
//}
//
//fun isOverlapping(newCenter: Offset, existingCenters: List<Offset>, circleRadius: Float): Boolean {
//    return existingCenters.any { existingCenter ->
//        hypot(newCenter.x - existingCenter.x, newCenter.y - existingCenter.y) <= 2 * circleRadius
//    }
//}