package com.cerebus.excersizesample.balls

import com.cerebus.excersizesample.api.ExcersizeData

sealed class Events {
    data object StartGame : Events()
    data class BallClicked(val number: Int) : Events()
    data class ChangeData(val data: ExcersizeData) : Events() // для начала можно просто число
    data object Success : Events()
    data object Lose : Events()
}

