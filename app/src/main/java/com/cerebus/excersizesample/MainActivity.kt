package com.cerebus.excersizesample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cerebus.excersizesample.balls.Events
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import com.cerebus.excersizesample.balls.presentation.screens.StepOneScreen
//import com.cerebus.excersizesample.balls.presentation.screens.StepTwoScreen
import com.cerebus.excersizesample.balls.presentation.screens.StepZeroScreen


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: BallsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BallsViewModel(this)
        enableEdgeToEdge()
        // Получаем последние параметры игры
        val lastGameParams = viewModel.getLastGameParams()
        val difficultLevel: Int = lastGameParams.difficultLevel
        val step: Int = lastGameParams.step
        setContent {
            when(step){
                0 -> {
                    StepZeroScreen(viewModel)
                }
                1 -> {
                    StepOneScreen(viewModel)
                }
                2 -> {
//                    StepTwoScreen(viewModel)
                }
                3 -> {

                }
            }

        }
    }
}