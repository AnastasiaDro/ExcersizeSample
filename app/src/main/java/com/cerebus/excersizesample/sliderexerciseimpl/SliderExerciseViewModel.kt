package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeListener
import com.cerebus.excersizesample.api.ExcersizeProvider
import com.cerebus.excersizesample.api.ExcersizeType
import com.cerebus.excersizesample.api.LevelData
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.ExerciseDataRepository
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderConstants
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderParameters
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.SliderTypeName
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

    private var _currentSliderType = mutableStateOf(sliderTypes[SliderTypeName.SHORT]!!)
    val currentSliderType: State<SliderParameters> = _currentSliderType

    var exerciseData: ExcersizeData = exerciseDataRepository.getLevel()

    private lateinit var screenSize: Pair<Int, Int>

    init {
        when (exerciseData.levelData.difficultLevel) {
            0 -> setSliderType(SliderTypeName.SHORT)
            1 -> setSliderType(SliderTypeName.SHORT_MOVED)
            2 -> setSliderType(SliderTypeName.MEDIUM)
            3 -> setSliderType(SliderTypeName.LONG)
            4 -> setSliderType(SliderTypeName.VERTICAL)
            5 -> setSliderType(SliderTypeName.ANGLED)
        }
    }

    fun getScreenSize(width: Int, height: Int) {
        screenSize = Pair(width, height)
    }

    fun setRandomStartPosition(width: Int, height: Int): Pair<Int, Int> {
        val marginedBoundX = width/2 - 60
        val marginedBoundY = height/2 - 60
        val randomStartX = (-marginedBoundX..marginedBoundX).random()
        val randomStartY = (-marginedBoundY..marginedBoundY).random()
        return Pair(randomStartX, randomStartY)
    }

    fun setRandomAngle(): Float {
        return (0..359).random().toFloat()
    }

    fun setSliderType(typeName: SliderTypeName) {
        _currentSliderType.value = sliderTypes[typeName] ?: sliderTypes[SliderTypeName.SHORT]!!
    }

    fun saveNewStatsAndCloseActivity() {
        saveNewStats()
        closeActivity()
    }

    private fun saveNewStats() {
        val currentSliderParameters = exerciseData.levelData.parameters as SliderParameters
        val allTypes = SliderTypeName.values()
        val currentIndex = allTypes.indexOf(currentSliderParameters.name)
        var newDifficultName = allTypes[currentIndex]

        // получение следующей сложности
        val nextSliderType = if (currentIndex in allTypes.indices) {
            val nextType = allTypes[currentIndex + 1]
            sliderTypes[nextType]
        } else {
            sliderTypes[SliderTypeName.SHORT]
        }

        //TODO исправить при переходе на следующее упражнение
        val newExerciseType = ExcersizeType.DRAG_SLIDER

        val newExerciseNumber = exerciseData.excersizeNumber + 1
        val newExerciseTimeLimit = exerciseData.excersizeTimeLimit
        var newDifficultLevel = exerciseData.levelData.difficultLevel
        var newStep = 0
        var newLength = nextSliderType?.length

        if (exerciseData.levelData.difficultLevel == 1 && exerciseData.levelData.step < 1) {
            newStep = exerciseData.levelData.step + 1
            newLength = currentSliderParameters.length
        } else {
            newDifficultLevel++
            newDifficultName = nextSliderType!!.name
        }
        // TODO timer
        val newSuccessTime = 0L


        // New Random Coords
        var newStartX = 0
        var newStartY = 0
        if (exerciseData.levelData.difficultLevel == 0 || exerciseData.levelData.difficultLevel == 1) {
            if (exerciseData.levelData.step < 1) {
                val calculation = setRandomStartPosition(screenSize.first, screenSize.second)
                newStartX = calculation.first
                newStartY = calculation.second
            } else {
                newStartX = 0
                newStartY = 0
            }
        }

        // New random angle
        var newAngle = 0f
        if (exerciseData.levelData.difficultLevel == 4) {
            newAngle = setRandomAngle()
        } else if (exerciseData.levelData.difficultLevel == 3) {
            newAngle = 90f
        }

        val newExerciseData = ExcersizeData(
            excersizeType = newExerciseType,
            excersizeNumber = newExerciseNumber,
            excersizeTimeLimit = newExerciseTimeLimit,
            levelData = LevelData(
                difficultLevel = newDifficultLevel,
                difficultName = newDifficultName.name,
                step = newStep,
                successTime = newSuccessTime,
                parameters = SliderParameters(
                    name = newDifficultName,
                    startX = newStartX,
                    startY = newStartY,
                    length = newLength ?: 100,
                    angle =  newAngle
                )
            )
        )

        //TODO удалить сброс на последнем уровне
        if (exerciseData.levelData.difficultLevel == 5) {
            exerciseDataRepository.clearPreferences()
        } else {
            exerciseDataRepository.saveLevel(
                excersizeData = newExerciseData
            )
        }
    }

    private val _closeActivityEvent = MutableSharedFlow<Unit>()
    val closeActivityEvent: SharedFlow<Unit> = _closeActivityEvent

    private fun closeActivity() {
        viewModelScope.launch {
            _closeActivityEvent.emit(Unit)
        }
    }
}

val sliderTypes = mapOf(
    SliderTypeName.SHORT to SliderParameters(SliderTypeName.SHORT, SliderConstants.SLIDER_LENGTH_SHORT, 0f, 0, 0),
    SliderTypeName.SHORT_MOVED to SliderParameters(SliderTypeName.SHORT_MOVED, SliderConstants.SLIDER_LENGTH_SHORT, 0f, 100, 100),
    SliderTypeName.MEDIUM to SliderParameters(SliderTypeName.MEDIUM,SliderConstants.SLIDER_LENGTH_MEDIUM, 0f, 0, 0),
    SliderTypeName.LONG to SliderParameters(SliderTypeName.LONG, SliderConstants.SLIDER_LENGTH_LONG, 0f, 0, 0),
    SliderTypeName.VERTICAL to SliderParameters(SliderTypeName.VERTICAL,SliderConstants.SLIDER_LENGTH_LONG, 90f, 0, 0),
    SliderTypeName.ANGLED to SliderParameters(SliderTypeName.ANGLED,SliderConstants.SLIDER_LENGTH_LONG, 45f, 0, 0)
)

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
