package com.cerebus.excersizesample.sliderexerciseimpl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.ExerciseDataRepository

class SliderExerciseViewModelFactory(
    private val exerciseDataRepository: ExerciseDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SliderExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SliderExerciseViewModel(exerciseDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
