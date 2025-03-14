package com.cerebus.excersizesample.impl

import com.cerebus.excersizesample.api.ExcersizeData
import com.cerebus.excersizesample.api.ExcersizeProvider


class YourExcersizeProvider : ExcersizeProvider {

    override fun getExcersize(): ExcersizeData {
        return ExcersizeData( ... ... ... )
    }
}