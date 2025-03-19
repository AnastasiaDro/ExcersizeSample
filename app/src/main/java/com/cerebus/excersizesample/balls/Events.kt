package com.cerebus.excersizesample.balls

sealed class Events {
//    data object GetStep : Events()
    data class BallClicked(val number: Int) : Events()
    data object ChangeData : Events()
    data object Success : Events()
    data object Lose : Events()

}

