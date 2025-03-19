package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeListener
import com.cerebus.excersizesample.api.ExcersizeProvider
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.ExerciseDataRepository
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderConstants
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderParameters
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SliderExerciseViewModel(private val exerciseDataRepository: ExerciseDataRepository) : ViewModel() {
    //ВАЖНО: в типе - интерфейс, а уже в значении - имлементация! Я потом тут коин подключу
    private val excersizeListener: ExcersizeListener = SliderExerciseListener()
    private val excersizeProvider: ExcersizeProvider = SliderExerciseProvider()

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

    val currentSliderType: State<SliderParameters> = _currentSliderType

    fun setSliderType(typeName: SliderTypeName) {
        _currentSliderType.value = sliderTypes[typeName] ?: sliderTypes[SliderTypeName.SHORT]!!
    }

    fun upgradeToNextSliderType() {
        when(currentSliderType.value.name) {
            SliderTypeName.SHORT -> setSliderType(SliderTypeName.SHORT_MOVED)
            SliderTypeName.SHORT_MOVED -> setSliderType(SliderTypeName.MEDIUM)
            SliderTypeName.MEDIUM -> setSliderType(SliderTypeName.LONG)
            SliderTypeName.LONG -> setSliderType(SliderTypeName.VERTICAL)
            SliderTypeName.VERTICAL -> setSliderType(SliderTypeName.ANGLED)
            SliderTypeName.ANGLED -> setSliderType(SliderTypeName.SHORT)
        }
    }

    fun saveNewStatsAndExit() {

    }
}

private val sliderTypes = mapOf(
    SliderTypeName.SHORT to SliderParameters(SliderTypeName.SHORT, SliderConstants.SLIDER_LENGTH_SHORT, 0f, 0, 0),
    SliderTypeName.SHORT_MOVED to SliderParameters(SliderTypeName.SHORT_MOVED, SliderConstants.SLIDER_LENGTH_SHORT, 0f, 100, 100),
    SliderTypeName.MEDIUM to SliderParameters(SliderTypeName.MEDIUM,SliderConstants.SLIDER_LENGTH_MEDIUM, 0f, 0, 0),
    SliderTypeName.LONG to SliderParameters(SliderTypeName.LONG, SliderConstants.SLIDER_LENGTH_LONG, 0f, 0, 0),
    SliderTypeName.VERTICAL to SliderParameters(SliderTypeName.VERTICAL,SliderConstants.SLIDER_LENGTH_LONG, 90f, 0, 0),
    SliderTypeName.ANGLED to SliderParameters(SliderTypeName.ANGLED,SliderConstants.SLIDER_LENGTH_LONG, 45f, 0, 0)
)

enum class SliderTypeName {
    SHORT,
    SHORT_MOVED,
    MEDIUM,
    LONG,
    VERTICAL,
    ANGLED
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
