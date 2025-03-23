package com.cerebus.excersizesample.balls.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import kotlin.random.Random

@Composable
fun RoundImageButton(color: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val borderColor = Color.DarkGray
    val borderWith = 5.dp
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(100.dp)
            .background(color, CircleShape)
            .border(shape = CircleShape, color = borderColor, width = borderWith)
    ) {
    }
}

@Composable
fun StepTwoScreen(viewModel: BallsViewModel) {
    // Состояния для отслеживания видимости кнопок
    var isOneButtonVisible by remember { mutableStateOf(true) }
    var isTwoButtonVisible by remember { mutableStateOf(true) }

    // Состояния для хранения позиций кнопок
    val random = remember { Random }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Генерация случайных позиций для кнопок при инициализации
    val buttonOnePosition by remember {
        mutableStateOf(
            Offset(
                x = random.nextInt(screenWidth.value.toInt() - 100).toFloat(),
                y = random.nextInt(screenHeight.value.toInt() - 100).toFloat()
            )
        )
    }
    val buttonTwoPosition by remember {
        mutableStateOf(
            Offset(
                x = random.nextInt(screenWidth.value.toInt() - 100).toFloat(),
                y = random.nextInt(screenHeight.value.toInt() - 100).toFloat()
            )
        )
    }

    LaunchedEffect(Unit) {
        Log.d("qaz", "showBall2")
        viewModel.updateStartTime(System.currentTimeMillis())
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Отображение первой кнопки, если она видима
        if (isOneButtonVisible) {
            RoundImageButton(
                color = Color.Blue,
                onClick = {
                    isOneButtonVisible = false
                    viewModel.ballClicked()
                },
                modifier = Modifier.offset(
                    x = buttonOnePosition.x.dp,
                    y = buttonOnePosition.y.dp
                )
            )
        }

        // Отображение второй кнопки, если она видима
        if (isTwoButtonVisible) {
            RoundImageButton(
                color = Color.Green,
                onClick = {
                    isTwoButtonVisible = false
                    viewModel.ballClicked()
                },
                modifier = Modifier.offset(
                    x = buttonTwoPosition.x.dp,
                    y = buttonTwoPosition.y.dp
                )
            )
        }
    }
}
