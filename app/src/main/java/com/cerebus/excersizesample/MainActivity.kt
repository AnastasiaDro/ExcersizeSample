package com.cerebus.excersizesample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cerebus.excersizesample.sliderexerciseimpl.sliderapi.ExerciseDataRepository
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseScreen
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseViewModel
import com.cerebus.excersizesample.sliderexerciseimpl.SliderExerciseViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SliderExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("exercise_prefs", Context.MODE_PRIVATE)
        val exerciseDataRepository = ExerciseDataRepository(sharedPreferences)

        val viewModelFactory = SliderExerciseViewModelFactory(exerciseDataRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SliderExerciseViewModel::class.java)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.closeActivityEvent.collect {
                    finish()
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            SliderExerciseScreen()
        }
    }
}