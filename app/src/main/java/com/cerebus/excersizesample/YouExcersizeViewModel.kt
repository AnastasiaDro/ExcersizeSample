package com.cerebus.excersizesample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class YouExcersizeViewModel : ViewModel() {

    //ВАЖНО: в типе - интерфейс, а уже в значении - имлементация! Я потом тут коин подключу
    private val excersizeListener: ExcersizeListener =  YourExcersizeListener()
    private val excersizeProvider: ExcersizeProvider = YourExcersizeProvider()

    /** Через эту подписку вы получаете упражнение **/
    private val _excersizeSharedFlow: MutableSharedFlow<Excersize> = MutableSharedFlow()
    val excersizeSharedFlow: SharedFlow<Excersize> = _excersizeSharedFlow

    /** Через эту подписку вы уведомляете View о начале/конце упраджнения **/
    private val _excersizeUpdateStateSharedFlow: MutableSharedFlow<ExcersizeAction> = MutableSharedFlow()
    private val excersizeUpdateStateSharedFlow: SharedFlow<ExcersizeAction> = _excersizeUpdateStateSharedFlow

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

    }
}

enum class ExcersizeAction {
    START,
    FINISH
}