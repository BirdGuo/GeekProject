package com.guoxw.geekproject.calendar.utils

import com.guoxw.geekproject.calendar.constant.CalenderConstant
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by guoxw on 2017/9/7 0007.
 * @auther guoxw
 * @date 2017/9/7 0007
 * @desciption
 * @package com.guoxw.geekproject.calendar.utils
 */
class CalendarUtil() {

    private var iday: Int = 0
    private var eventArr: List<Map<String, String>>? = null
    private var pickedEvents: MutableList<Map<String, String>>? = null
    private var picked_events: MutableList<Map<String, String>>? = null

    val goodList: MutableList<Map<String, String>> = ArrayList()
    val badList: MutableList<Map<String, String>> = ArrayList()

    private fun random(dayseed: Int, indexseed: Int): Int {
        var n = dayseed % 11117
        for (i in 0 until 100 + indexseed) {
            n *= n
            n %= 11117
        }
        return n
    }

    private fun pickTodaysLuck() {
        val numGood = random(iday, 98) % 3 + 2
        val numBad = random(iday, 87) % 3 + 2

        eventArr = pickRandomActivity(numGood + numBad)

//        for (i in 0 until numGood - 1) {
//            goodList.add(eventArr!![i])
//        }

        /**
         *for循环变种写法
         */
        (0 until numGood - 1).mapTo(goodList) { eventArr!![it] }
        (0 until numBad - 1).mapTo(badList) { eventArr!![numGood + it] }

    }

    private fun pickRandomActivity(size: Int): MutableList<Map<String, String>> {
        picked_events = pickRandom(CalenderConstant.getActivities(), size)
        pickedEvents = ArrayList()

        var map: Map<String, String>? = null

        for (i in 0 until picked_events!!.size - 1) {
            map = parse(picked_events!![i])
            pickedEvents!!.add(map)
        }

        return pickedEvents!!
    }

    private fun parse(pickRes: Map<String, String>): Map<String, String> {
        val result = HashMap<String, String>()
        result.put("name", pickRes["name"]!!)
        result.put("good", pickRes["good"]!!)
        result.put("bad", pickRes["bad"]!!)
        val rName = result["name"]
        if (rName!!.indexOf("%v") != -1) {
            val name = rName.replace("%v", CalenderConstant.varNames[random(iday, 12) % CalenderConstant.varNames.size])
            result.put("name", name)
        }

        if (rName.indexOf("%t") != -1) {
            val name = rName.replace("%t", CalenderConstant.tools[random(iday, 11) % CalenderConstant.tools.size])
            result.put("name", name)
        }

        if (rName.indexOf("%l") != -1) {
            val name = rName.replace("%l", (random(iday, 12) % 247 + 30).toString())
            result.put("name", name)
        }
        return result
    }

    private fun pickRandom(array: MutableList<Map<String, String>>, size: Int): MutableList<Map<String, String>> {
        val result = ArrayList<Map<String, String>>()
        result += array

        (0 until array.size - size)
                .asSequence()
                .map { random(iday, it) % result.size }
                .forEach { result.removeAt(it) }
        return result
    }

    private fun pickRandomList(array: Array<String>, size: Int): List<String> {
        val result = ArrayList<String>()
        result += array

        (0 until array.size - size)
                .asSequence()
                .map { random(iday, it) % result.size }
                .forEach { result.removeAt(it) }
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

    init {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        iday = year * 10000 + month * 100 + day
        pickTodaysLuck()
    }

}