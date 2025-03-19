package com.cerebus.excersizesample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.ExerciseDataRepository
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseScreen
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseViewModel
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SliderExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("exercise_prefs", Context.MODE_PRIVATE)
        val exerciseDataRepository = ExerciseDataRepository(sharedPreferences)

        val viewModelFactory = SliderExerciseViewModelFactory(exerciseDataRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SliderExerciseViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            SliderExerciseScreen()
        }
    }
}