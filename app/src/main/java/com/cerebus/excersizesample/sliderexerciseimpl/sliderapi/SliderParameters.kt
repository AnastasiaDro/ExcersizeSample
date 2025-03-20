package com.cerebus.excersizesample.sliderexerciseimpl.sliderapi

import com.cerebus.excersizesample.api.Parameters
import com.cerebus.excersizesample.sliderexerciseimpl.SliderTypeName

data class SliderParameters(
    val name: SliderTypeName,
    val length: Int,
    val angle: Float,
    var startX: Int,
    val startY: Int
) : Parameters
