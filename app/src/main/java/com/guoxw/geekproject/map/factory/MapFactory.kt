package com.guoxw.geekproject.map.factory

import android.content.Context
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.baidu.mapapi.map.BaiduMap
import com.guoxw.geekproject.map.factory.manager.AMapManager
import com.guoxw.geekproject.map.factory.manager.BMapManager

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.factory
 * @desciption
 */
/**
 * 地图操作工厂
 * @param mContext 上下文
 * @param aMap 高德地图
 * @param baiduMap 百度地图
 */
class MapFactory(
        /**
         * 上下文
         */
        val mContext: Context,
        /**
         * 高德地图
         */
        aMapView: MapView?,
        /**
         * 百度地图
         */
        baiduMapView: com.baidu.mapapi.map.MapView?) {

    var iMapManager: IMapManager? = null

    companion object Factory {
        /**
         * 创建工厂类
         * @param mContext 上下文
         * @param aMap 高德地图
         */
        fun create(mContext: Context, aMapView: MapView): IMapManager = MapFactory(mContext, aMapView, null).iMapManager!!

        /**
         * 创建工厂类
         * @param mContext 上下文
         * @param baiduMap 百度地图
         */
        fun create(mContext: Context, baiduMapView: com.baidu.mapapi.map.MapView): IMapManager = MapFactory(mContext, null, baiduMapView).iMapManager!!
    }

    init {
        //初始化工厂操作方法
        iMapManager = if (aMapView != null) {
            //高德地图
            AMapManager(mContext, aMapView!!)
        } else {
            //百度地图
            BMapManager(mContext, baiduMapView!!)
        }
    }

}