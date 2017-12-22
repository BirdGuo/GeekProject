package com.guoxw.geekproject.map.api

import android.content.Context
import com.amap.api.maps.AMap
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.guoxw.geekproject.map.bean.Station
import io.reactivex.Observable

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.api
 * @desciption
 */
interface MapApi {

    /**
     * 从文件中读取数据
     */
    fun readStationsFromAsset(mContext: Context, name: String): Observable<String>

    /**
     * 从数据库中选取所有定位点
     */
    fun selectStationAllFromDB(): Observable<MutableList<Station>>

    /**
     * 添加定位点到地图上
     */
    fun addStationsToMap(markerOptionsListAll: MutableList<MarkerOptions>, list: MutableList<Station>,
                         mContext: Context, amap: AMap, clickedMarker: Marker?): Observable<MutableList<Station>>

    /**
     * 保存定位点到数据库
     */
    fun saveStationsToDB()

}