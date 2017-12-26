package com.guoxw.geekproject.map.bean

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.bean
 * @desciption
 */
class MyLocation(
        /**
         * 纬度
         */
        var lat: Double = 0.0,
        /**
         * 经度
         */
        var lng: Double = 0.0,
        /**
         * 详细地址
         */
        var address: String = "",
        /**
         * 街道
         */
        var street: String = "",
        /**
         * 区
         */
        var area: String = "",
        /**
         * 市
         */
        var city: String = "",
        /**
         * 省
         */
        var province: String = "")