package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt

@Composable
fun SliderExerciseScreen() {
    val sliderExerciseViewModel: SliderExersizeViewModel = viewModel()
    val sliderType = sliderExerciseViewModel.currentSliderType.value

    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ToggleSlider(
                    sliderType,
                    modifier = Modifier.padding(innerPadding),
                    onSliderCompleted = { sliderExerciseViewModel.saveNewStatsAndExit() },
                )
            }
        }
    }
}

@Composable
fun ToggleSlider(
    sliderType: SliderType,
    modifier: Modifier,
    onSliderCompleted: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    val toggleWidth = sliderType.length.dp
    val toggleAngle = sliderType.angle
    val toggleHeight = 40.dp
    val thumbSize = 40.dp
    val toggleRange = with(LocalDensity.current) { (toggleWidth - thumbSize).toPx() }

    val currentSliderType by rememberUpdatedState(sliderType)

    Box(
        modifier = Modifier
            .width(toggleWidth)
            .height(toggleHeight)
            .rotate(toggleAngle)
            .offset(x = sliderType.startX.dp, y = sliderType.startY.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (isCompleted) Color.Green else Color.Gray)
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
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.Cyan)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderScreenPreview() {
    SliderExerciseScreen()
}
