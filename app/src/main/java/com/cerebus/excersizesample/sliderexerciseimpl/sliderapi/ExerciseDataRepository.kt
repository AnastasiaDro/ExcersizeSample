package com.cerebus.excersizesample.sliderexerciseimpl.sliderapi

import android.content.SharedPreferences
import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeType
import com.cerebus.excersizesample.api.LevelData
import com.cerebus.excersizesample.sliderexerciseimpl.SliderTypeName

/*
* Использую для доступа к SharedPreferences
* */

private const val EXERCISE_TYPE = "excersize_type"
private const val EXERCISE_NUMBER = "excersize_number"
private const val EXERCISE_TIME_LIMIT = "excersize_time_limit"

private const val EXERCISE_DIFFICULT_LEVEL = "excersize_difficult_level"
private const val EXERCISE_DIFFICULT_NAME = "excersize_difficult_name"
private const val EXERCISE_STEP = "excersize_step"
private const val EXERCISE_SUCCESS_TIME = "excersize_success_time"

private const val IS_SLIDER_IN_PROGRESS = "is_slider_in_progress"

private const val SLIDER_NAME = "slider_name"
private const val SLIDER_START_X = "slider_start_x"
private const val SLIDER_START_Y = "slider_start_y"


class ExerciseDataRepository(private val sharedPreferences: SharedPreferences) {

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveLevel(excersizeData: ExcersizeData) {
        val sliderParameters = excersizeData.levelData.parameters as SliderParameters

        val editor = sharedPreferences.edit()
        editor
            .putString(EXERCISE_TYPE, ExcersizeType.DRAG_SLIDER.name)
            .putInt(EXERCISE_NUMBER, excersizeData.excersizeNumber)
            .putLong(EXERCISE_TIME_LIMIT, SliderConstants.EXERCISE_TIME_LIMIT)

            .putInt(EXERCISE_DIFFICULT_LEVEL, excersizeData.levelData.difficultLevel)
            .putString(EXERCISE_DIFFICULT_NAME, excersizeData.levelData.difficultName)
            .putInt(EXERCISE_STEP, excersizeData.levelData.step)
            .putLong(EXERCISE_SUCCESS_TIME, excersizeData.levelData.successTime)

            .putString(SLIDER_NAME, sliderParameters.name.name)
            .putInt(SLIDER_START_X, sliderParameters.startX)
            .putInt(SLIDER_START_Y, sliderParameters.startY)
        editor.apply()
    }

    fun getLevel(): ExcersizeData {
        return if (sharedPreferences.getInt(EXERCISE_NUMBER, -1) == -1) {
            getInitialLevel()
        } else {
            getInProgressLevel()
        }
    }

    fun getInitialLevel(): ExcersizeData = ExcersizeData(
        excersizeType = ExcersizeType.DRAG_SLIDER,
        excersizeNumber = 0,
        excersizeTimeLimit = 20000,
        levelData = LevelData(
            difficultLevel = 0,
            difficultName = SliderTypeName.SHORT.name,
            step = 0,
            successTime = 0,
            parameters = SliderParameters(
                name = SliderTypeName.SHORT,
                length = 0,
                angle = 0f,
                startX = 0,
                startY = 0
            )
        )
    )

    private fun getInProgressLevel(): ExcersizeData {
        val excersizeType: ExcersizeType = ExcersizeType.DRAG_SLIDER
        val excersizeNumber: Int = sharedPreferences.getInt(
            EXERCISE_NUMBER,
            0
        ) //порядковый номер упражнения, сквозной для всех заданий
        val excersizeTimeLimit: Long = sharedPreferences.getLong(
            EXERCISE_TIME_LIMIT, 20000
        )  //То самое время (20 секунд давайте брать), сколько мы показываем наш баннер, затем скрываем

        return ExcersizeData(
            excersizeType = excersizeType,
            excersizeNumber = excersizeNumber,
            excersizeTimeLimit = excersizeTimeLimit,
            levelData = getLevelData()
        )
    }

    private fun getLevelData(): LevelData {
        val difficultLevel = sharedPreferences.getInt(EXERCISE_DIFFICULT_LEVEL, 0)
        val difficultName = sharedPreferences.getString(EXERCISE_DIFFICULT_NAME, "no difficulty name")
        val step = sharedPreferences.getInt(EXERCISE_STEP, 0)
        val successTime = sharedPreferences.getLong(EXERCISE_SUCCESS_TIME, 0)
        return LevelData(
            difficultLevel = difficultLevel,
            difficultName = difficultName!!,
            step = step,
            successTime = successTime,
            parameters = getParameters()
        )
    }

    private fun getParameters(): SliderParameters {
        val preferencesName: String = sharedPreferences.getString(
                        SLIDER_NAME, "no slider name")!!
        val name = when(preferencesName) {
            SliderTypeName.SHORT.name -> SliderTypeName.SHORT
            SliderTypeName.SHORT_MOVED.name -> SliderTypeName.SHORT_MOVED
            SliderTypeName.MEDIUM.name -> SliderTypeName.MEDIUM
            SliderTypeName.LONG.name -> SliderTypeName.LONG
            SliderTypeName.VERTICAL.name -> SliderTypeName.VERTICAL
            SliderTypeName.ANGLED.name -> SliderTypeName.VERTICAL
            else -> SliderTypeName.SHORT
        }

        val startX = sharedPreferences.getInt(SLIDER_START_X, 0)
        val startY = sharedPreferences.getInt(SLIDER_START_Y, 0)

        return SliderParameters(
            name = name,
            length = 0,
            angle = 0f,
            startX = startX,
            startY = startY
        )
    }
}
