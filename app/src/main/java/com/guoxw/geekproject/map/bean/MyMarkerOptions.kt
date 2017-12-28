package com.guoxw.geekproject.map.bean

import android.os.Bundle

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.bean
 * @desciption
 */
/**
 * 自定义marker参数
 * @param myLatLng 定位经纬度
 * @param title 标题
 * @param bundle 附加信息 高德marker不能添加bundle
 * @param iconFromReource 图标
 */
class MyMarkerOptions(
        /**
         * 经纬度
         */
        val myLatLng: MyLatLng,
        /**
         * 标题
         */
        val title: String?,
        /**
         * 附加信息
         */
        val bundle: Bundle?,
        /**
         * 图标
         */
        val iconFromReource: Int)