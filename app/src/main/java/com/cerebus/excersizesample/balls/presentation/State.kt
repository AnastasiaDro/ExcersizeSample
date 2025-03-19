package com.cerebus.excersizesample.balls.presentation

import android.text.format.DateUtils
import androidx.compose.ui.graphics.Color
import com.cerebus.excersizesample.balls.AllConstants
import java.time.LocalDate
import kotlin.random.Random

data class BallsUiItems(
    val circleRadius: Float = 200.0f, // радиус
    val circleColor: Color, // цвет шара
    val crossColor: Color = Color.Black,
    val crossLength: Float = circleRadius * 1f, // Длина линий крестика
    val crossThickness: Float = 10f, // Толщина линий крестика
)

// GameState - это текущее состояние игры
data class GameState (
    val difficultLevel: Int = 0, //у каждого упражнения есть сложность, например, тстаичные шарики - это сложность 0,  // двигающиеся - сложность 2
    val step: Int = 0, // шаг во время сложности, то есть 1 шарик - ноль, два шарика - 1, 3 шарика - 2
    val successTime: Long = AllConstants.TIME_FOR_SUCCESS_STEP_0_1, // время в миллисекундах, при котором мы считаем упражнение успешно выполненным FULL_SUCCESS
//    val isShowBall : Boolean = true, // шарик показан или нет
    val numberOfBalls : Int = 1, // число шариков на экране
    val lastGameParams: LastGameParams = LastGameParams()
)


// LastGameParams - это парамеры, которые сохраняются в SharedPref
data class LastGameParams(
    val difficultLevel: Int = 0, // уровень сложности нужно запустить (статика = 0, движ = 1)
    val step: Int = 0, // шаг сложности (уровень) нужно запустить, от 0 до 3 согласно ТЗ
    val isGameSucceed : Boolean = false, // уровень успешно завершен за нужное время
    val timeGameFinished : Long = 0L, // за сколько секунд завершен уровень
    val gameSucceedTimes : Int = 0, // сколько раз игра была успешно сыграна до последнего уровня
    val lastSucceedDate: LocalDate = LocalDate.now() // день, когда игра была успешно сыграна до последнего уровня
)





