package com.guoxw.geekproject.map.factory

import android.content.Context
import com.amap.api.maps.AMap
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
        val aMap: AMap?,
        /**
         * 百度地图
         */
        val baiduMap: BaiduMap?) {

    var iMapManager: IMapManager? = null

    companion object Factory {
        /**
         * 创建工厂类
         * @param mContext 上下文
         * @param aMap 高德地图
         */
        fun create(mContext: Context, aMap: AMap): IMapManager = MapFactory(mContext, aMap, null).iMapManager!!

        /**
         * 创建工厂类
         * @param mContext 上下文
         * @param baiduMap 百度地图
         */
        fun create(mContext: Context, baiduMap: BaiduMap): IMapManager = MapFactory(mContext, null, baiduMap).iMapManager!!
    }

    init {
        //初始化工厂操作方法
        iMapManager = if (aMap != null) {
            //高德地图
            AMapManager(aMap!!)
        } else {
            //百度地图
            BMapManager(baiduMap!!)
        }
    }

}