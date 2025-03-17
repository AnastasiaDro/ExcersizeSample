package com.cerebus.excersizesample.sliderexerciseimpl

import android.content.Context

/*
* Использую для доступа к SharedPreferences
* */
class DataRepository(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveLevel(number: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("sliderLevel", number)
        editor.apply()
    }

    fun getLevel(): Int {
        return sharedPreferences.getInt("sliderLevel", 0)
    }
}