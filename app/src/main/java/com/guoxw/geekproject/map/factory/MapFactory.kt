package com.guoxw.geekproject.map.factory

import android.content.Context
import com.amap.api.maps.AMap
import com.baidu.mapapi.map.BaiduMap
import com.guoxw.geekproject.map.factory.manager.AMapManager
import com.guoxw.geekproject.map.factory.manager.BMapManager

/**
 * @auther guoxw
 * @date 2017/12/28 0028
 * @package com.guoxw.geekproject.map.factory
 * @desciption
 */
/**
 * 地图操作工厂
 */
class MapFactory(val mContext: Context, val aMap: AMap?, val baiduMap: BaiduMap?) {

    var iMapManager: IMapManager? = null

    companion object Factory {
        fun create(mContext: Context, aMap: AMap): IMapManager = MapFactory(mContext, aMap, null).iMapManager!!
        fun create(mContext: Context, baiduMap: BaiduMap): IMapManager = MapFactory(mContext, null, baiduMap).iMapManager!!
    }

    init {
        iMapManager = if (aMap != null) {
            AMapManager(aMap!!)
        } else {
            BMapManager(baiduMap!!)
        }
    }

}