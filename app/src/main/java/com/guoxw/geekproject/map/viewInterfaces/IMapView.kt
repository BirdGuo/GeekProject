package com.guoxw.geekproject.map.viewInterfaces

import com.guoxw.geekproject.map.bean.Station

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.viewInterfaces
 * @desciption
 */
interface IMapView {

    /**
     * 添加地图点成功
     */
    fun addStationsSuccess()

    /**
     * 添加到地图点失败
     * @param error 失败原因
     */
    fun addStationsFail(error:String)

    /**
     * 从数据库中选取定位点成功
     * @param stations 定位点集合
     */
    fun selectStationFromDBSuccess(stations: MutableList<Station>)

    /**
     * 从数据库中选取定位点失败
     * @param error 失败原因
     */
    fun selectStationFromDBFail(error: String)

}