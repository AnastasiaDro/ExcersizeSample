package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderParameters
import kotlin.math.roundToInt

@Composable
fun SliderExerciseScreen() {
    val sliderExerciseViewModel: SliderExerciseViewModel = viewModel()
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp }
    val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp }
    val sliderType = sliderExerciseViewModel.exerciseData.levelData.parameters as SliderParameters

    LaunchedEffect(Unit) {
        sliderExerciseViewModel.getScreenSize(screenWidth, screenHeight)
    }

    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ChangeableSlider(
                    sliderType,
                    modifier = Modifier.padding(innerPadding),
                    onSliderCompleted = { sliderExerciseViewModel.saveNewStatsAndCloseActivity() },
                )
            }
        }
    }
}

@Composable
fun ChangeableSlider(
    sliderType: SliderParameters,
    modifier: Modifier = Modifier,
    onSliderCompleted: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    val sliderWidth = sliderType.length.dp
    val sliderAngle = sliderType.angle
    val sliderHeight = 40.dp
    val innerSliderHeight = 36.dp
    val thumbSize = 40.dp
    val toggleRange = with(LocalDensity.current) { (sliderWidth - thumbSize).toPx() }

    // Внешние границы
    Box(
        modifier = Modifier
            .width(sliderWidth)
            .height(sliderHeight)
            .offset(x = sliderType.startX.dp, y = sliderType.startY.dp)
            .rotate(sliderAngle)
            .background(Color.Transparent)
    ) {
        // Слайдер подложка тонкая
        Box(
            modifier = Modifier
                .height(innerSliderHeight)
                .width(sliderWidth)
                .clip(RoundedCornerShape(18.dp))
                .align(Alignment.Center)
                .background(color = if (isCompleted) Color.Green else Color.LightGray)
        )
        // Слайдер функциональный
        Box(
            modifier = Modifier
                .width(sliderWidth)
                .height(sliderHeight)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Transparent)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX = (offsetX + dragAmount.x).coerceIn(0f, toggleRange)
                            isCompleted = offsetX >= toggleRange * 0.99
                        },
                        onDragEnd = {
                            if (isCompleted) {
                                onSliderCompleted()
                            }
                            offsetX = 0f
                            isCompleted = false
                        }
                    )
                }
        ) {
            // Кнопка слайдера
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) }
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(Color(0xff74d476))
                    .border(4.dp, Color(0xffcbf0ce), CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SliderScreenPreview() {
    SliderExerciseScreen()
}
