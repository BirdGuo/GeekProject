package com.guoxw.geekproject.map.factory

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.factory
 * @desciption
 */
/**
 * 工厂操作方法接口
 */
interface ILocation {

    /**
     * 初始化定位参数
     * @param time 定位间隔时间
     */
    fun initLcationOption(time: Long)

    /**
     * 开始定位
     */
    fun startLocation()

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