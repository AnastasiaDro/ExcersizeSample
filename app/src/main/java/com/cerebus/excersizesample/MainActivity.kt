package com.cerebus.excersizesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import com.cerebus.excersizesample.balls.presentation.StepOneScreen
import com.cerebus.excersizesample.balls.presentation.StepTwoScreen
import com.cerebus.excersizesample.balls.presentation.StepZeroScreen

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: BallsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BallsViewModel()
        enableEdgeToEdge()

        val startStepFromPreferences = 2// это нужно получить из sharedPref

        setContent {
            when(startStepFromPreferences){
                0 -> {
                    StepZeroScreen(viewModel)
                }
                1 -> {
                    StepOneScreen(viewModel)
                }
                2 -> {
                    StepTwoScreen(viewModel)
                }
                3 -> {

                }
            }

        }
    }
}