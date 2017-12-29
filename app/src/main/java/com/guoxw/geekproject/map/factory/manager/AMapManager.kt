package com.guoxw.geekproject.map.factory.manager

import android.content.Context
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
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
         * 上下文
         */
        val mContext: Context,
        /**
         * 高德地图控件
         */
        private val aMapView: MapView) : IMapManager {

    /**
     * 高德地图控制器
     */
    var aMap: AMap? = null

    init {
        aMap = aMapView.map
    }

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
        aMap!!.addMarker(markerOptions)

    }

    override fun setZoomLevel(zoomLevel: Float) {
        aMap!!.maxZoomLevel = zoomLevel
    }

    override fun mapCreate(savedInstanceState: Bundle?) {
        aMapView.onCreate(savedInstanceState)
    }


    override fun mapResume() {
        aMapView.onResume()
    }

    override fun mapDestroy() {
        aMapView.onDestroy()
    }

    override fun mapPause() {
        aMapView.onPause()
    }

}