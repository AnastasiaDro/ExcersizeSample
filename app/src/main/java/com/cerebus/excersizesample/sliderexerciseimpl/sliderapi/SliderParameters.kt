package com.cerebus.excersizesample.sliderexerciseimpl.sliderapi

import com.cerebus.excersizesample.api.Parameters
import com.cerebus.excersizesample.sliderexerciseimpl.SliderTypeName

data class SliderParameters(
    val name: SliderTypeName,
    val length: Int = 0,
    val angle: Float = 0f,
    var startX: Int = 0,
    val startY: Int = 0
) : Parameters
