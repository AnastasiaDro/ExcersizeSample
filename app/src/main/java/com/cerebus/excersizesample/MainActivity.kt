package com.cerebus.excersizesample

//import com.cerebus.excersizesample.balls.presentation.screens.StepTwoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cerebus.excersizesample.api.ResultValue
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import com.cerebus.excersizesample.balls.presentation.screens.StepOneScreen
import com.cerebus.excersizesample.balls.presentation.screens.StepZeroScreen


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: BallsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BallsViewModel(this)
        enableEdgeToEdge()
        // Получаем последние параметры игры
        val lastGameParams = viewModel.getLastGameParams()
        var step = if (lastGameParams.isGameSucceedStatus == ResultValue.FULL_SUCCESS) {
            lastGameParams.step + 1
        } else {
            lastGameParams.step
        }
        setContent {
            when (step) {
                0 -> {
                    viewModel.levelDataUpdate(0) // тут обновление параметров для текущего уровня
                    StepZeroScreen(viewModel)
                }

                1 -> {
                    viewModel.levelDataUpdate(1)
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