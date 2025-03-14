package com.cerebus.excersizesample

/**
 * если вам тут что-то не хватает - создайте интерфейс и унаследуйтесь от этого,
 * добавьте необходимые параметры
 * Ну или скописасьтите к херам =)
 *
 */
interface ExcersizeListener {

    /**
     * Тут мы обрабатываем что-либо, можно добавить сюда мсохранение результата в sharedPrefs
     */
    fun userInteracted(): ResultValue
}