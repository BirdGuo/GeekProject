package com.guoxw.geekproject.map.utils

import android.content.Context
import android.graphics.Point
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.guoxw.geekproject.map.bean.MyMarkerCluster
import com.guoxw.geekproject.utils.ScreenUtil

/**
 * @auther guoxw
 * @date 2017/12/19 0019
 * @package com.guoxw.geekproject.map.utils
 * @desciption
 */
object CluterUtil {

    /**
     * 计算聚合点
     *
     * @param mContext
     * @param amap
     * @param markerOptionsListAll
     * @param markerOptionsListInView
     * @param markers
     * @param clickedMarker
     */
    fun resetMarks(mContext: Context, amap: AMap,
//                   markerOptionsListInView: MutableList<MarkerOptions>,
                   markerOptionsListAll: MutableList<MarkerOptions>,
                   markers: MutableList<Marker>, clickedMarker: Marker?) {

        val markerOptionsListInView: MutableList<MarkerOptions> = ArrayList()
        val screenHeight = ScreenUtil.getScreenHeight(mContext)
        val screenWidth = ScreenUtil.getScreenWidth(mContext)

        val projection = amap.projection
        var point: Point? = null
        //清除视野内所有点
        markerOptionsListInView.clear()
        /**
         *聚合点集合
         */
        val myClusterMarkers: MutableList<MyMarkerCluster> = ArrayList()
        //把所有
        markerOptionsListAll.filter {
            //判断是否超出边界
            point = projection.toScreenLocation(it.position)
            point!!.x > 0 && point!!.y > 0 && point!!.x < screenWidth && point!!.y < screenHeight
        }.forEach {
            markerOptionsListInView.add(it)
            //遍历屏幕内的所有点
            if (myClusterMarkers.size == 0) {//第一个聚合点，也是聚合中心点
                myClusterMarkers.add(MyMarkerCluster(mContext, projection, it, 100))//添加聚合点，范围为100
            } else {
                var isIn: Boolean = false
                myClusterMarkers.filter { mit ->
                    mit.bounds!!.contains(it.position)//根据边界判断该点是否在聚合中心点的范围内
                }.forEach { mit ->
                    //在范围内
                    mit.addMarker(it)//添加到聚合点集合
                    isIn = true
                }
                if (!isIn) {//不在范围内
                    //新建一个聚合点
                    myClusterMarkers.add(MyMarkerCluster(mContext, projection, it, 100))
                }

            }

        }

        myClusterMarkers.forEach {
            //遍历所有聚合点
            it.setPositionAndIcon()//设置聚合点位置和icon
        }

        if (markers.size != 0) {//清除除定位点以外的Marker
            markers.forEach {
                it.remove()//删除当前marker。在删除当前marker之后，它的所有方法将不被支持。
                // marker.destroy();//删除当前marker并销毁Marker的图片等资源。一旦使用此方法，该对象将被彻底释放。
            }
        }
        markers.clear()//清空数组

        if (clickedMarker != null) {//有点击过的点
            if (clickedMarker.title != "-1") {//点击的不是聚合点
                val title = clickedMarker.title//获取marker的title
                val splite = title.split(",")
                var id = 0
                id = splite[1].toInt()//获取marker图标resource id
                val myMarkerCluster = myClusterMarkers[0]//获取头一个聚合点
                val options = myMarkerCluster.options
                options.icon(BitmapDescriptorFactory.fromResource(id))//设置图标
                //添加聚合点
                addMarkers(amap, myClusterMarkers, markers)
                //添加点击的点
                val addMarker = amap.addMarker(options)
                addMarker.setToTop()//显示在最上层
                markers.add(addMarker)
            } else {
                addMarkers(amap, myClusterMarkers, markers)
            }
        } else {
            addMarkers(amap, myClusterMarkers, markers)
        }

    }

    /**
     * 通过数组添加marker
     * @param aMap
     * @param cluters
     * @param markers
     */
    private fun addMarkers(aMap: AMap, cluters: MutableList<MyMarkerCluster>, markers: MutableList<Marker>) {
        cluters.forEach {
            val addMarker = aMap.addMarker(it.options)
            addMarker.setToTop()
            //加入数组
            markers.add(addMarker)
        }
    }

}