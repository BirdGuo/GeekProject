package com.guoxw.geekproject.calendar.utils

import com.guoxw.geekproject.calendar.constant.CalenderConstant
import java.util.*

/**
 * Created by guoxw on 2017/9/7 0007.
 * @auther guoxw
 * @date 2017/9/7 0007
 * @desciption
 * @package com.guoxw.geekproject.calendar.utils
 */
class CalendarUtil {

    var iday: Int = 0

    constructor() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        iday = year * 10000 + month * 100 + day
    }

    private fun random(dayseed: Int, indexseed: Int): Int {
        var n = dayseed % 11117
        for (i in 0..100 + indexseed - 1) {
            n = n * n
            n = n % 11117
        }
        return n
    }

    private fun pickRandomList(array: Array<String>, size: Int): List<String> {
        val result = ArrayList<String>()
        for (str in array) {
            result.add(str)
        }

        for (j in 0..array.size - size - 1) {
            val index = random(iday, j) % result.size
            result.removeAt(index)
        }
        return result
    }

    fun initDirection(): String {
        return CalenderConstant.directions[random(iday, 2) % CalenderConstant.directions.size]
    }

    fun initDrinks(): String {
        val drinks = pickRandomList(CalenderConstant.drinks, 2)
        return drinks[0].plus(",").plus(drinks[1])
    }

    /**
     *
     * @return 返回女神指数
     */
    fun getGirlValue(): Double {
        return random(iday, 6) % 50 / 10.0
    }

}