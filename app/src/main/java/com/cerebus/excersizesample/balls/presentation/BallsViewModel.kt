package com.cerebus.excersizesample.balls.presentation

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.cerebus.excersizesample.balls.AllConstants

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import com.cerebus.excersizesample.api.ResultValue
import kotlinx.coroutines.flow.update

class BallsViewModel(private val context: Context) : ViewModel() {
    private val sharedPreferences = context.getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)
    private val _state: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    // Функция для сохранения данных в sharedPref
    fun saveLastGameParams(params: LastGameParams) {
        sharedPreferences.edit() {
            putInt("difficultLevel", params.difficultLevel)
            putInt("step", params.step)
            putString("isGameSucceedStatus", params.isGameSucceedStatus.name)
            putLong("timeGameFinished", params.timeGameFinished)
            putLong("lastSucceedTime", params.lastSucceedTime)
        }
    }
    // Функция для получения данных
    fun getLastGameParams(): LastGameParams {
        val difficultLevel = sharedPreferences.getInt("difficultLevel", 0)
        val step = sharedPreferences.getInt("step", 0)
        val isGameSucceedStatusString = sharedPreferences.getString("isGameSucceedStatus", ResultValue.UNSUCCESS.name)
        val isGameSucceedStatus = ResultValue.valueOf(isGameSucceedStatusString ?: ResultValue.UNSUCCESS.name)
        val timeGameFinished = sharedPreferences.getLong("timeGameFinished", 0L)
        val lastSucceedTime = sharedPreferences.getLong("lastSucceedTime", 0L)
        return LastGameParams(difficultLevel, step, isGameSucceedStatus, timeGameFinished, lastSucceedTime)
    }

    fun updateStartTime(currentTime: Long) {
        _state.value = _state.value.copy(startTime = currentTime)
    }

    fun  checkTimeSuccess() : Boolean{
        val currentTime = System.currentTimeMillis()
        val finishTime = currentTime - _state.value.startTime
        _state.update { it.copy(timeGameFinished = finishTime) }
        return finishTime <= state.value.successTime
    }


    fun changeDataInSharedPref(){
        val newData = LastGameParams(
                    difficultLevel = state.value.difficultLevel,
                    step = state.value.step,
                    isGameSucceedStatus = state.value.gameResultValue,
                    lastSucceedTime = System.currentTimeMillis(),
                    timeGameFinished = state.value.timeGameFinished
                )

        updateDataForScreen() // тут обновляем через state, чтобы отобразить на экране
        saveLastGameParams(newData)
    }

    fun endOfZeroLevel() {
        val levelFinishedInTime = checkTimeSuccess()
        if (levelFinishedInTime){
            _state.update {
                it.copy(
                    gameResultValue = ResultValue.FULL_SUCCESS,
                )
            }
        }
        else{
            _state.update {
                it.copy(
                    gameResultValue = ResultValue.PART_SUCCESS,
                )
            }
        }
        changeDataInSharedPref()

    }

    fun endOfOneLevel(){
        val levelFinishedInTime = checkTimeSuccess()
        if (levelFinishedInTime){
            _state.update {
                it.copy(
                    gameResultValue = ResultValue.FULL_SUCCESS,
                )
            }
        }
        else{
            _state.update {
                it.copy(
                    gameResultValue = ResultValue.PART_SUCCESS,
                )
            }
        }
        changeDataInSharedPref()

    }


    fun ballClicked(){
        /*тут считаем количество шариков
        если все шарики нажаты едем дальше
         */

        // if (шариков нет)
        when(state.value.step){
            0 -> { endOfZeroLevel() }
            1 -> { endOfOneLevel() }
            2-> {}
            3 -> {}
        }

        // переписать завершение активити!!
//                (context as? Activity)?.finish() // херня какая-то, но я спать хочу уже

        /* шарик нажат, проверяем сколько шариков осталось нажать
    если шариков нет -> то вызыем checkData ()
    в checkData проверяем  текущие значения игры с референсными 4
    и выставляем FULL_SUCCESS, UNSUCCESS, или LOSE -> затем
    ChangeData записываем данные в SharedPref ->
    затем выходим из игры
     */
    }

    fun levelDataUpdate(level : Int){
        when(level){
            0 -> { _state.update {
                    it.copy(
                        difficultLevel = 0,
                        difficultName = "Static",
                        step = 0,
                        successTime = AllConstants.TIME_FOR_SUCCESS_STEP_0_1,
//                        numberOfBalls = 1,
                        numberBallsAll = 1,
                    )
                }
            }
            1 -> {
                _state.update {
                    it.copy(
                        difficultLevel = 0,
                        difficultName = "Static",
                        step = 1,
                        successTime = AllConstants.TIME_FOR_SUCCESS_STEP_0_1,
//                        numberOfBalls = 1,
                        numberBallsAll = 1,
                    )
                }
            }
            2 -> {
                _state.update {
                    it.copy(
                        difficultLevel = 0,
                        difficultName = "Static",
                        step = 2,
                        successTime = AllConstants.TIME_FOR_SUCCESS_STEP_2_3,
//                        numberOfBalls = 1,
                        numberBallsAll = 2,
                    )
                }
            }
            3 -> {
                _state.update {
                    it.copy(
                        difficultLevel = 0,
                        difficultName = "Static",
                        step = 3,
                        successTime = AllConstants.TIME_FOR_SUCCESS_STEP_2_3,
//                        numberOfBalls = 1,
                        numberBallsAll = 3,
                    )
                }
            }
        }
        Log.d("qaz", "GameData For level ${state.value.step}: " +
                "sucTime : ${state.value.successTime}, ballsAll: ${state.value.numberBallsAll} ")
    }

    fun updateDataForScreen(){
        _state.update { it.copy(
            lastGameParams = LastGameParams(
                difficultLevel = state.value.difficultLevel,
                step = state.value.step,
                isGameSucceedStatus = state.value.gameResultValue,
                lastSucceedTime = System.currentTimeMillis(),
                timeGameFinished = state.value.timeGameFinished,
            )
        ) }
    }

}
