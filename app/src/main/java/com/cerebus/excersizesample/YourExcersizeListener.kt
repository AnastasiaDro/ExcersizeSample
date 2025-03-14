package com.cerebus.excersizesample

import android.util.Log

class YourExcersizeListener : ExcersizeListener {

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
        private const val TAG = "YourExcersizeListener"
    }
}
