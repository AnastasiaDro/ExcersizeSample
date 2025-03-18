package com.cerebus.excersizesample.balls.impl

import android.content.Context
import androidx.core.content.edit

class DataRepository(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("BallsPref", Context.MODE_PRIVATE)

    fun saveLevel(number: Int) {
        sharedPreferences.edit() {
            putInt("balls", number)
        }
    }

    fun getLevel(): Int {
        return sharedPreferences.getInt("balls", 0)
    }
}