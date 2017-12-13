package com.guoxw.geekproject.utils

import android.annotation.SuppressLint
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by GXW on 2016/10/28 0028.
 * email:603004002@qq.com
 */
object TimeUtil {


    /**
     * 获取当前时间
     *
     * @return String
     *
     * 当前时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date(System.currentTimeMillis()))// 获取系统时间
    }


    /**
     * 获取当前时间
     *
     * @return String
     *
     * 当前时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(Date(System.currentTimeMillis()))// 获取系统时间
    }


    /**
     * 获取当前时间
     *
     * @return String
     *
     * 当前时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowDateZH(): String {
        val dateFormat = SimpleDateFormat("yyyy年MM月dd日")
        return dateFormat.format(Date(System.currentTimeMillis()))// 获取系统时间
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private fun strToDate(strDate: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val pos = ParsePosition(0)
        return formatter.parse(strDate, pos)
    }


    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private fun getWeek(sdate: String): String {
        // 再转换为时间

        val date = TimeUtil.strToDate(sdate)
        val c = Calendar.getInstance()
        c.time = date
        // int hour=c.get(Calendar.DAY_OF_WEEK);

        // hour中存的就是星期几了，其范围 1~7

        // 1=星期日 7=星期六，其他类推

        return SimpleDateFormat("EEEE").format(c.time)
    }

    /**
     * 获取周几
     * @param sdate
     * @return
     */
    fun getWeekStr(sdate: String): String {
        var str = ""
        str = TimeUtil.getWeek(sdate)
        when (str) {
            "1" -> str = "星期日"
            "2" -> str = "星期一"
            "3" -> str = "星期二"
            "4" -> str = "星期三"
            "5" -> str = "星期四"
            "6" -> str = "星期五"
            "7" -> str = "星期六"
        }
        return str
    }


}