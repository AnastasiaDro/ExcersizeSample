package com.cerebus.excersizesample.balls.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerebus.excersizesample.MainActivity
import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeListener
import com.cerebus.excersizesample.api.ExcersizeProvider
import com.cerebus.excersizesample.api.ResultValue
import com.cerebus.excersizesample.impl.YourExcersizeListener
import com.cerebus.excersizesample.impl.YourExcersizeProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BallsViewModel() : ViewModel() {

    //ВАЖНО: в типе - интерфейс, а уже в значении - имлементация! Я потом тут коин подключу
    private val excersizeListener: ExcersizeListener =  YourExcersizeListener()
    private val excersizeProvider: ExcersizeProvider = YourExcersizeProvider()

    /** Через эту подписку вы получаете упражнение **/
    private val _excersizeSharedFlow: MutableSharedFlow<ExcersizeData> = MutableSharedFlow()
    val excersizeSharedFlow: SharedFlow<ExcersizeData> = _excersizeSharedFlow

    /** Через эту подписку вы уведомляете View о начале/конце упраджнения **/
    private val _excersizeUpdateStateSharedFlow: MutableSharedFlow<ExcersizeAction> = MutableSharedFlow()
    val excersizeUpdateStateSharedFlow: SharedFlow<ExcersizeAction> = _excersizeUpdateStateSharedFlow

    fun getExcersize() {
        viewModelScope.launch {
            _excersizeSharedFlow.emit(excersizeProvider.getExcersize())
        }
    }

    /**
     * ВАЖНО: у Тани будет +- такая функция, у вас может быть другая! Может быть не touched,
     * а там "вышел за границы полоски" или "отпустил во время обводки полоску"
     * Суть в том, что эта функция - вход в наш "excersizeListener'
     * @param x - x-координата касания
     * @param y - y-координата касания
     * @param isTarget - попал ли в шарик
     */
    fun screenWasTouched(x: Float, y: Float, isTarget: Boolean) {
        val result = excersizeListener.userInteracted()
        if (result == ResultValue.FULL_SUCCESS || result == ResultValue.PART_SUCCESS)
            viewModelScope.launch {
                _excersizeUpdateStateSharedFlow.emit(ExcersizeAction.FINISH)
            }
    }

    /////
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()


}


enum class ExcersizeAction {
    START,
    FINISH,
}

/**
 * FULL_SUCCESS - полный успех, ребенок сделал задание и уложился в желаемое время (successTime)
 * PART_SUCCESS - ребенок сделал задание, но не уложился в желаемое время (successTime)
 * UNSUCCESS - ребенок не сделал задание за ответеденное время вообще
 * Про время см класс []
 */
