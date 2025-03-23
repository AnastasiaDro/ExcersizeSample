package com.cerebus.excersizesample.balls.presentation

import androidx.compose.ui.graphics.Color
import com.cerebus.excersizesample.api.ResultValue
import com.cerebus.excersizesample.balls.AllConstants

    data class Ball(
        val id: Int, // Уникальный идентификатор шарика
        val x: Float, // Координата X центра шарика
        val y: Float, // Координата Y центра шарика
        val radius: Float, // Радиус шарика
        val color: Color,
        var isVisible: Boolean = true // Видим ли шарик
    )

data class BallsUiItems(
    val circleRadius: Float = 200.0f, // радиус
    val circleColor: Color, // цвет шара
    val borderColor: Color = Color.DarkGray, // обводка
    val crossLength: Float = circleRadius * 1f, // Длина линий крестика
    val borderThickness: Float = 10f, // Толщина линий обводки
)



// GameState - это текущее состояние игры
data class GameState (
    val difficultLevel: Int = 0,
    val difficultName : String = "Static Ball",//у каждого упражнения есть сложность, например, тстаичные шарики - это сложность 0,  // двигающиеся - сложность 2
    val step: Int = 0, // шаг во время сложности, то есть 1 шарик - ноль, два шарика - 1, 3 шарика - 2
    val successTime: Long = AllConstants.TIME_FOR_SUCCESS_STEP_0_1, // время в миллисекундах, при котором мы считаем упражнение успешно выполненным FULL_SUCCESS
    val numberBallsAll : Int = 1,
    val startTime: Long = 0L,  // Время начала игры
    val timeGameFinished :  Long = 0L, // за сколько секунд завершен уровень
    val gameResultValue: ResultValue = ResultValue.UNSUCCESS,
    val lastGameParams: LastGameParams = LastGameParams(),
)


// LastGameParams - это парамеры, которые сохраняются в SharedPref
data class LastGameParams(
    val difficultLevel: Int = 0, // уровень сложности нужно запустить (статика = 0, движ = 1) //   в целом нафиш не надо,
    val step: Int = 0, // шаг сложности (уровень) нужно запустить, от 0 до 3 согласно ТЗ
    val isGameSucceedStatus : ResultValue = ResultValue.UNSUCCESS, // уровень успешно завершен за нужное время
    val timeGameFinished : Long = 0L, // за сколько секунд завершен уровень
    val lastSucceedTime: Long = 0L, // System.currentTimeMillis()
)




