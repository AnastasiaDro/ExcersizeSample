package com.cerebus.excersizesample.balls.presentation

import androidx.compose.ui.graphics.Color
import com.cerebus.excersizesample.balls.AllConstants

data class State (
    val difficultLevel: Int = 0, //у каждого упражнения есть сложность, например, тстаичные шарики - это сложность 0,  // двигающиеся - сложность 2
    val difficultName: String = "name of level" ,//обзовите тут сложность. Например "шарики статичны" (только на английском) или "прямой слайдер"
    val step: Int = 0, // шаг во время сложности, то есть 1 шарик - ноль, два шарика - 1, 3 шарика - 2
    val successTime: Long = AllConstants.TIME_FOR_SUCCESS_STEP_0_1, // время в миллисекундах, при котором мы считаем упражнение успешно выполненным FULL_SUCCESS
    val parameters: BallsParameters = BallsParameters(),// экземпляр класса параметров
    val isShowBall : Boolean = true,
)

data class BallsParameters(
    val circleRadius: Float = 200.0f,
    val circleColor: Color = Color.Red,
    val crossColor: Color = Color.Black,
    val crossLength: Float = circleRadius * 1f,// Длина линий крестика
    val crossThickness: Float = 10f,// Толщина линий крестика
)
