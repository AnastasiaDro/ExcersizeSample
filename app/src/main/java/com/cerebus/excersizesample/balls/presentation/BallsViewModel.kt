package com.cerebus.excersizesample.balls.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.cerebus.excersizesample.api.ExcersizeType
import com.cerebus.excersizesample.balls.AllConstants
import com.cerebus.excersizesample.balls.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import java.lang.Thread.sleep
import java.nio.channels.ShutdownChannelGroupException
import kotlin.system.exitProcess

class BallsViewModel(private val context: Context) : ViewModel() {
    private val sharedPreferences = context.getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)
    private val _state: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()
    // Функция для сохранения данных
    fun saveLastGameParams(params: LastGameParams) {
        sharedPreferences.edit() {
            putInt("difficultLevel", params.difficultLevel)
            putInt("step", params.step)
            putBoolean("isGameSucceed", params.isGameSucceed)
            putLong("timeGameFinished", params.timeGameFinished)
            putInt("gameSucceedTimes", params.gameSucceedTimes)
        }
    }
    // Функция для получения данных
    fun getLastGameParams(): LastGameParams {
        val difficultLevel = sharedPreferences.getInt("difficultLevel", 0)
        val step = sharedPreferences.getInt("step", 0)
        val isGameSucceed = sharedPreferences.getBoolean("isGameSucceed", false)
        val timeGameFinished = sharedPreferences.getLong("timeGameFinished", 0L)
        val gameSucceedTimes = sharedPreferences.getInt("gameSucceedTimes", 0)

        return LastGameParams(difficultLevel, step, isGameSucceed, timeGameFinished, gameSucceedTimes)
    }
    fun sendEvent(event: Events) {
        when (event) {
            is Events.BallClicked -> {
                /* шарик нажат, проверяем сколько шариков осталось нажать
                если шариков нет -> то вызыем checkData ()
                в checkData проверяем  текущие значения игры с референсными 4
                и выставляем FULL_SUCCESS, UNSUCCESS, или LOSE -> затем
                ChangeData записываем данные в SharedPref ->
                затем выходим из игры
                 */
            }
            is Events.Success -> {}
            is Events.Lose -> {}
            is Events.ChangeData -> {
                val newData = LastGameParams(
                    step = 1,
                    difficultLevel = 0
                )
                _state.update {
                    it.copy(
                        lastGameParams = newData
                    )
                }
                saveLastGameParams(newData)
                // тут надо выйти из приложения, сохранив всё в sharedPref

                // переписать!
                (context as? Activity)?.finish() // херня какая-то, но я спать хочу уже

            }
        }
    }
}


