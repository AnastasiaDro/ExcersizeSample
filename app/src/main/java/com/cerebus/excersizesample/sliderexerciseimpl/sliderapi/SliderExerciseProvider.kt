package com.cerebus.excersizesample.sliderexerciseimpl.sliderapi

import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeProvider

class SliderExerciseProvider(reposa: ExerciseDataRepository) : ExcersizeProvider {
    val reposa = reposa

    override fun getExcersize(): ExcersizeData {
        return reposa.getLevel()
    }
}