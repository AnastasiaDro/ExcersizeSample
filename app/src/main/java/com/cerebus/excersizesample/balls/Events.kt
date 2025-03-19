package com.cerebus.excersizesample.balls

import com.cerebus.excersizesample.balls.presentation.LastGameParams

sealed class Events {
    data object GetStep : Events()
    data class BallClicked(val number: Int) : Events()
    data object ChangeData : Events()
    data object Success : Events()
    data object Lose : Events()
}

