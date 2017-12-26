package com.guoxw.geekproject.map.bean

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.amap.api.maps.Projection
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions
import com.guoxw.geekproject.R
import com.guoxw.geekproject.utils.BitmapUtil

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
class MyMarkerCluster(
        val mContext: Context,
        /**
         * 高德屏幕坐标系
         */
        private val projection: Projection,
        /**
         * 中心点参数
         */
        private val centerMarkerOptions: MarkerOptions,
        /**
         * 区域大小参数
         */
        private val gridSize: Int) {

    /**
     * 聚合点参数
     */
    var options: MarkerOptions = MarkerOptions()
    /**
     * 距中心点的范围
     */
    var bounds: LatLngBounds? = null

    private var includeMarkers: MutableList<MarkerOptions> = ArrayList()

//    private var markerCluster10

    init {
        val point: Point = projection.toScreenLocation(centerMarkerOptions.position)
        val southWestPoint = Point(point.x - gridSize, point.y + gridSize)
        val northEastPoint = Point(point.x + gridSize, point.y - gridSize)

        bounds = LatLngBounds(projection.fromScreenLocation(southWestPoint), projection.fromScreenLocation(northEastPoint))
        //设置参数
        options.anchor(0.5f, 0.5f)//设置Marker覆盖物的锚点比例
                .title(centerMarkerOptions.title)//设置标题
                .position(centerMarkerOptions.position)//设置坐标
                .icon(centerMarkerOptions.icon)//设置Marker覆盖物的动画帧图标列表，多张图片模拟gif的效果
                .snippet(centerMarkerOptions.snippet)//设置 Marker覆盖物的 文字描述

        //添加中心点
        includeMarkers.add(centerMarkerOptions)

    }

    /**
     * 添加marker
     * @param markerOptions
     */
    fun addMarker(markerOptions: MarkerOptions) {
        includeMarkers.add(markerOptions)
    }

    /**
     * 设置聚合点位置坐标和图标
     */
    fun setPositionAndIcon() {
        val size = includeMarkers.size
        if (size == 1)
            return

        var lat = 0.0
        var lng = 0.0
        /**
         * 地点描述
         */
        var snippet = ""

        includeMarkers.asSequence()
                .forEach {
                    lat += it.position.latitude
                    lng += it.position.longitude
                    snippet += it.title + "\n"//所有marker描述
                }

        /**
         * 求经纬度的平均数
         */
        options.position(LatLng(lat / size, lng / size))// 设置中心位置为聚合点的平均位置
        options.title("-1")
        options.snippet(snippet)//设置描述

        val iconType = size / 10
        when (iconType) {
            0 -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_10))))
            1 -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_20))))
            2 -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_30))))
            3 -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_30))))
            4 -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_50))))
            else -> options.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.viewToBitmap(
                    setMarkerViewContent(size, R.mipmap.marker_cluster_100))))
        }
    }

    /**
     * 设置聚合点背景和数字
     * @param size
     * @param resourceId
     *
     * @return view
     */
    private fun setMarkerViewContent(size: Int, resourceId: Int): View {
        //获取页面
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_marker_cluster, null)
        //获取数字
        val tv_marker_cluster = view.findViewById<TextView>(R.id.tv_marker_cluster)
        //获取聚合点背景
        val rv_marker_cluster = view.findViewById<RelativeLayout>(R.id.rv_marker_cluster)

        //设置聚合点背景
        rv_marker_cluster.setBackgroundResource(resourceId)
        //设置聚合点数字
        tv_marker_cluster.text = size.toString()
        return view
    }


}