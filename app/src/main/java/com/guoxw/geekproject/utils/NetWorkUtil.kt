package com.guoxw.geekproject.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by guoxw on 2017/12/6 0006.
 */
object NetWorkUtil {

    /**
     * 判断是否WiFi连接
     * @param context 上下文
     * @return true: WiFi连接；false 未连接WiFi
     */
    fun isWifiConnect(context: Context): Boolean {
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return networkInfo.isConnected

    }
}