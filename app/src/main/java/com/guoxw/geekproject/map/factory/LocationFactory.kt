package com.guoxw.geekproject.map.factory

import android.content.Context
import com.guoxw.geekproject.map.LocationTypeMode
import com.guoxw.geekproject.map.factory.interfaces.MyILocation
import com.guoxw.geekproject.map.factory.manager.AMapLocationManager
import com.guoxw.geekproject.map.factory.manager.BMapLocationManager

/**
 * @auther guoxw
 * @date 2017/12/25 0025
 * @package com.guoxw.geekproject.map
 * @desciption 定位工厂模式
 */
class LocationFactory(
        /**
         * 定位地图选择
         * 高德或者百度
         * @see LocationTypeMode
         */
        val locationType: Int,
        /**
         * 上下文
         */
        val mContext: Context,
        /**
         * 是否单次定位
         */
        val isOnceLocation: Boolean,
        /**
         * 定位回调监听
         */
        val myILocation: MyILocation) {

    /**
     * 工厂方法类
     */
    var iLocation: ILocation? = null


    companion object Factory {
        /**
         * 单例模式获取工厂类
         * @param locationType 定位地图选择
         * @see LocationTypeMode
         * @param mContext 上下文
         * @param isOnceLocation 是否单次定位
         * @param myILocation 定位回调监听
         */
        fun create(locationType: Int, mContext: Context, isOnceLocation: Boolean, myILocation: MyILocation): LocationFactory
                = LocationFactory(locationType, mContext, isOnceLocation, myILocation)
    }

    init {
        when (locationType) {//定位模式选择
        //高德定位模式
            LocationTypeMode.AMapMode -> iLocation = AMapLocationManager(mContext, myILocation, isOnceLocation)
        //百度定位模式
            LocationTypeMode.BaiduMapMode -> iLocation = BMapLocationManager(mContext, myILocation, isOnceLocation)
        }
    }

}