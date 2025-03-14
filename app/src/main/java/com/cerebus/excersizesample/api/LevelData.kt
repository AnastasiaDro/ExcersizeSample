package com.cerebus.excersizesample.api

/**
 * У каждого упражнения есть уровни, как в игре
 */
data class LevelData (
    val difficultLevel: Int, //у каждого упражнения есть сложность, например, тстаичные шарики - это сложность 0,
                            // двигающиеся - сложность 2
    val difficultName: String, //обзовите тут сложность. Например "шарики статичны" (только на английском) или "прямой слайдер"
    val step: Int, // шаг во время сложности, то есть 1 шарик - ноль, два шарика - 1, 3 шарика - 2
    /** @see ResultValue **/
    val successTime: Long, // время в миллисекундах, при котором мы считаем упражнение успешно выполненным FULL_SUCCESS
    /** @see Parameters **/
    val parameters: Parameters // экземпляр класса параметров
)
