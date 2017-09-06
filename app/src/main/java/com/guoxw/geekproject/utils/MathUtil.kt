package com.guoxw.geekproject.utils

import java.util.*

/**
 * Created by gxw on 17-9-7.
 */
object MathUtil {

    fun randomInt(range: Int): Int {
        val random = Random()
        return Math.abs(random.nextInt()) % range
    }

    fun randomDouble(): Double {
        val random = Random()
        val index = random.nextDouble() * 10
        return index
    }
}