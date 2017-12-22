package com.guoxw.geekproject.map.presenter.dao

import android.content.Context
import com.amap.api.maps.AMap
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.guoxw.geekproject.map.bean.Station
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.presenter.dao
 * @desciption
 */
interface MapDao {

    interface MapView : IMapView

    interface FileView : IFileView

    interface Presenter {

        /**
         * 从文件中读取数据
         */
        fun readStationsFromAsset(mContext: Context, name: String)

        /**
         * 添加定位点到地图上
         */
        fun addStationsToMap(markerOptionsListAll: MutableList<MarkerOptions>, list: MutableList<Station>,
                             mContext: Context, amap: AMap, clickedMarker: Marker?)

        /**
         * 保存定位点到数据库
         */
        fun saveStationsToDB()

        /**
         * 从数据库选取定位点
         */
        fun selectStationAllFromDB()

    }

}