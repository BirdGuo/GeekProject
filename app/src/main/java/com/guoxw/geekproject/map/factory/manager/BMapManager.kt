package com.guoxw.geekproject.map.factory.manager

import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.model.LatLng
import com.guoxw.geekproject.map.bean.MyMarkerOptions
import com.guoxw.geekproject.map.factory.IMapManager

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.factory.manager
 * @desciption
 */
class BMapManager(val baiduMap: BaiduMap) : IMapManager {

    override fun addMarker(myMarkerOptions: MyMarkerOptions) {

        //声明marker参数
        val markerOptions = MarkerOptions()
        //设置marker位置
        markerOptions.position(LatLng(myMarkerOptions.myLatLng.latitude, myMarkerOptions.myLatLng.latitude))
        //设置title
        if (myMarkerOptions.title != null) {
            markerOptions.title(myMarkerOptions.title)
        }
        //设置额外信息
        if (myMarkerOptions.bundle != null) {
            markerOptions.extraInfo(myMarkerOptions.bundle)
        }
        //添加覆盖物
        baiduMap.addOverlay(markerOptions)

    }

    override fun setZoomLevel(zoomLevel: Float) {
        //设置地图缩放级别
        val mapStatus = MapStatus.Builder().zoom(zoomLevel).build()
        //更新参数
        val newMapStatus = MapStatusUpdateFactory.newMapStatus(mapStatus)
        //设置到地图上
        baiduMap.setMapStatus(newMapStatus)
    }

}