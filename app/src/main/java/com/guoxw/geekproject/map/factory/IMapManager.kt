package com.guoxw.geekproject.map.factory

import com.guoxw.geekproject.map.bean.MyLatLng
import com.guoxw.geekproject.map.bean.MyMarkerOptions

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.factory
 * @desciption
 */
/**
 * 地图操作工厂接口
 */
interface IMapManager {

    /**
     * 添加地图marker
     * @param myMarkerOptions 定位点参数
     * @see MyMarkerOptions
     */
    fun addMarker(myMarkerOptions: MyMarkerOptions)

    /**
     * 设置地图缩放级别
     * @param zoomLevel 缩放级别
     */
    fun setZoomLevel(zoomLevel: Float)

}