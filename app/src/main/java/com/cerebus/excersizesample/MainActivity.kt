package com.cerebus.excersizesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cerebus.excersizesample.api.ResultValue
import com.cerebus.excersizesample.balls.presentation.BallsViewModel
import com.cerebus.excersizesample.balls.presentation.screens.StepOneScreen
import com.cerebus.excersizesample.balls.presentation.screens.StepThreeScreen
import com.cerebus.excersizesample.balls.presentation.screens.StepTwoScreen
import com.cerebus.excersizesample.balls.presentation.screens.StepZeroScreen
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: BallsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BallsViewModel(this)
        enableEdgeToEdge()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.closeActivityEvent.collect {
                    finish()
                }
            }
        }
        val lastGameParams = viewModel.getLastGameParams()
        var step = if (lastGameParams.isGameSucceedStatus == ResultValue.FULL_SUCCESS) {
            lastGameParams.step + 1
        } else {
            lastGameParams.step
        }
        if (step > MAX_LEVEL_IN_GAME)
            step = 0
        viewModel.levelDataUpdate(step)
        setContent {
            when (step) {
                0 -> { StepZeroScreen(viewModel) }
                1 -> { StepOneScreen(viewModel) }
                2 -> { StepTwoScreen(viewModel) }
                3 -> { StepThreeScreen(viewModel) }
            }
        }
    }

    companion object {
        const val MAX_LEVEL_IN_GAME = 3
    }
}


