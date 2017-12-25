package com.guoxw.geekproject.utils

import android.content.Context

/**
 * @auther guoxw
 * @date 2017/12/19 0019
 * @package com.guoxw.geekproject.utils
 * @desciption
 */
object ScreenUtil {

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 得到设备屏幕的宽度
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 得到设备屏幕的高度
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 得到设备的密度
     */
    private fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * 把密度转换为像素
     */
    fun dip2px(context: Context, px: Float): Int {
        val scale = getScreenDensity(context)
        return (px * scale + 0.5).toInt()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}