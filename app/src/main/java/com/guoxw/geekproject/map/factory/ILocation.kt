package com.guoxw.geekproject.map.factory

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.factory
 * @desciption
 */
interface ILocation {

    /**
     * 初始化定位参数
     */
    fun initLcationOption(time: Long)

    /**
     * 开始定位
     */
    fun startLocation(force: Boolean)

    /**
     * 结束定位
     */
    fun stopLocation()

    /**
     * 是否正在定位
     */
//    fun isLocating(): Boolean

    /**
     * 是否定位成功
     */
//    fun isLocatingSuccess(): Boolean

}