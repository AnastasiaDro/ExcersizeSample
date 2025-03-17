package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeListener
import com.cerebus.excersizesample.api.ExcersizeProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SliderExersizeViewModel : ViewModel() {
    //ВАЖНО: в типе - интерфейс, а уже в значении - имлементация! Я потом тут коин подключу
    private val excersizeListener: ExcersizeListener = SliderExersizeListener()
    private val excersizeProvider: ExcersizeProvider = SliderExersizeProvider()

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

    private val _currentSliderType = mutableStateOf(sliderTypes[SliderTypeName.SHORT]!!)

    val currentSliderType: State<SliderType> = _currentSliderType

    fun setSliderType(typeName: SliderTypeName) {
        _currentSliderType.value = sliderTypes[typeName] ?: sliderTypes[SliderTypeName.SHORT]!!
    }

    fun upgradeToNextSliderType() {
        when(currentSliderType.value.name) {
            SliderTypeName.SHORT -> setSliderType(SliderTypeName.SHORT_MOVED)
            SliderTypeName.SHORT_MOVED -> setSliderType(SliderTypeName.MEDIUM)
            SliderTypeName.MEDIUM -> setSliderType(SliderTypeName.LONG)
            SliderTypeName.LONG -> setSliderType(SliderTypeName.VERTICAL)
            SliderTypeName.VERTICAL -> setSliderType(SliderTypeName.SLOPING)
            SliderTypeName.SLOPING -> setSliderType(SliderTypeName.SHORT)
        }
    }

    fun saveNewStatsAndExit() {

    }
}

data class SliderType(val name: SliderTypeName, val length: Int, val angle: Float, val startX: Int, val startY: Int)

private val sliderTypes = mapOf(
    SliderTypeName.SHORT to SliderType(SliderTypeName.SHORT, 100, 0f, 0, 0),
    SliderTypeName.SHORT_MOVED to SliderType(SliderTypeName.SHORT_MOVED, 100, 0f, 100, 100),
    SliderTypeName.MEDIUM to SliderType(SliderTypeName.MEDIUM,150, 0f, 0, 0),
    SliderTypeName.LONG to SliderType(SliderTypeName.LONG,200, 0f, 0, 0),
    SliderTypeName.VERTICAL to SliderType(SliderTypeName.VERTICAL,200, 90f, 0, 0),
    SliderTypeName.SLOPING to SliderType(SliderTypeName.SLOPING,200, 45f, 0, 0)
)

enum class SliderTypeName {
    SHORT,
    SHORT_MOVED,
    MEDIUM,
    LONG,
    VERTICAL,
    SLOPING
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
