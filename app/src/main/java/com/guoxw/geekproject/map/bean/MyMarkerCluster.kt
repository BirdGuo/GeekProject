package com.guoxw.geekproject.map.bean

import android.content.Context
import android.graphics.Point
import com.amap.api.maps.Projection
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions

/**
 * @auther guoxw
 * @date 2017/12/19 0019
 * @package com.guoxw.geekproject.map.bean
 * @desciption
 */

/**
 * 构造器
 * @param mContext 上下文
 * @param projection 高德坐标系
 * @param centerMarkerOptions 中心点参数
 * @param gridSize 区域大小参数
 */
class MyMarkerCluster(val mContext: Context, val projection: Projection, val centerMarkerOptions: MarkerOptions, val gridSize: Int) {

    /**
     *
     */
    var options: MarkerOptions = MarkerOptions()
    /**
     * 距中心点的范围
     */
    private var bounds: LatLngBounds? = null

    private var includeMarkers: MutableList<MarkerOptions> = ArrayList()

    init {
        val point: Point = projection.toScreenLocation(centerMarkerOptions.position)
        val southWestPoint = Point(point.x - gridSize, point.y + gridSize)
        val northEastPoint = Point(point.x + gridSize, point.y - gridSize)

        bounds = LatLngBounds(projection.fromScreenLocation(southWestPoint), projection.fromScreenLocation(northEastPoint))
        //设置参数
        options.anchor(0.5f, 0.5f).title(centerMarkerOptions.title)
                .position(centerMarkerOptions.position)
                .icon(centerMarkerOptions.icon)
                .snippet(centerMarkerOptions.snippet)

        //添加中心点
        includeMarkers.add(centerMarkerOptions)

    }

}