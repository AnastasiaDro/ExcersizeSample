package com.cerebus.excersizesample.api

/**
 * Этот интерфейс занимается тем, что берет
 * откуда-либо упражнения, например, рассчитывает логику действий
 */
interface ExcersizeProvider {

    fun getExcersize(): ExcersizeData
}