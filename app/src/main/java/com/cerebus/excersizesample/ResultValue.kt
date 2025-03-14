package com.cerebus.excersizesample

/**
 * FULL_SUCCESS - полный успех, ребенок сделал задание и уложился в желаемое время (successTime)
 * PART_SUCCESS - ребенок сделал задание, но не уложился в желаемое время (successTime)
 * UNSUCCESS - ребенок не сделал задание за ответеденное время вообще
 * Про время см класс []
 */
enum class ResultValue {
    FULL_SUCCESS,
    PART_SUCCESS,
    UNSUCCESS,
}