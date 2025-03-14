package com.cerebus.excersizesample.api

/**
 * Класс, описывающий задание
 */
data class ExcersizeData(
    val excersizeType: ExcersizeType,
    val excersizeNumber: Int, //порядковый номер упражнения, сквозной для всех заданий
    val excersizeTimeLimit: Long,  //То самое время (20 секунд давайте брать), сколько мы показываем наш баннер, затем скрываем
    val levelData: LevelData //описание уровня
)


