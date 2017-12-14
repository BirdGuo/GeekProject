package com.guoxw.geekproject.map.api

import android.content.Context
import rx.Observable

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
     * 添加定位点到地图上
     */
    fun addStationsToMap()

    /**
     * 保存定位点到数据库
     */
    fun saveStationsToDB()

}