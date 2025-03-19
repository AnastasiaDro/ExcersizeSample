package com.cerebus.excersizesample.sliderexerciseimpl

import android.util.Log
import com.cerebus.excersizesample.api.ExcersizeListener
import com.cerebus.excersizesample.api.ResultValue

class SliderExerciseListener : ExcersizeListener {

    /**
     * какие-то методы обрабюотки
     * Проверяем успех или неуспех
     */
    override fun userInteracted(): ResultValue {
        Log.d(TAG, "user interracted")
        return ResultValue.FULL_SUCCESS
    }

    /** поменяйте тут тег! **/
    companion object {
        private const val TAG = "SliderExcersizeListener"
    }
}
