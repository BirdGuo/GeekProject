package com.guoxw.geekproject.map.factory.manager

import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.guoxw.geekproject.map.bean.MyMarkerOptions
import com.guoxw.geekproject.map.factory.IMapManager

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.factory.manager
 * @desciption
 */
/**
 * 高德地图工厂操作方法
 * @param aMap 地图
 */
class AMapManager(
        /**
         * 地图
         */
        val aMap: AMap) : IMapManager {

    override fun addMarker(myMarkerOptions: MyMarkerOptions) {

        //初始化定位参数
        val markerOptions = MarkerOptions()
        //设置标题
        if (myMarkerOptions.title != null) {
            markerOptions.title(myMarkerOptions.title!!)
        }
        //设置图标
        markerOptions.icon(BitmapDescriptorFactory.fromResource(myMarkerOptions.iconFromReource))
        //设置定位点
        markerOptions.position(LatLng(myMarkerOptions.myLatLng.latitude, myMarkerOptions.myLatLng.longitude))
        //添加marker
        aMap.addMarker(markerOptions)

    }

    override fun setZoomLevel(zoomLevel: Float) {
        aMap.maxZoomLevel = zoomLevel
    }
}