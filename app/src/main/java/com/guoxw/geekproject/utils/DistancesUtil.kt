package com.guoxw.geekproject.utils

import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng

import java.math.BigDecimal

/**
* @auther tom
* @date 2016/1/14 0014
* @package ${PACKAGE_NAME}
* @desciption
*/
object DistancesUtil {

    /**
     * 获取地图上两个点的距离
     *
     * 单位千米
     *
     * @param start
     * @param end
     * @return
     */
    fun getDistance(start: LatLng, end: LatLng): Float {

        val v = AMapUtils.calculateLineDistance(start, end)
        val disf = v / 1000
        val b = BigDecimal(disf.toDouble())
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()

    }

}
