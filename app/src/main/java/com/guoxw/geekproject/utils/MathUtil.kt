package com.guoxw.geekproject.utils

import java.util.*

/**
 * @auther gxw
 * @date 17-9-7
 * @package ${PACKAGE_NAME}
 * @desciption
 */
object MathUtil {

    fun randomInt(range: Int): Int {
        val random = Random()
        return Math.abs(random.nextInt()) % range
    }

    fun randomDouble(): Double {
        val random = Random()
        return random.nextDouble() * 10
    }
}