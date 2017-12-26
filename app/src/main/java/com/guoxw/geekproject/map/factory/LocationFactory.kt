package com.guoxw.geekproject.map.factory

import android.content.Context
import com.guoxw.geekproject.map.LocationTypeMode
import com.guoxw.geekproject.map.factory.interfaces.MyILocation
import com.guoxw.geekproject.map.factory.manager.AMapLocationManager
import com.guoxw.geekproject.map.factory.manager.BMapLocationManager

/**
 * @auther guoxw
 * @date 2017/12/25 0025
 * @package com.guoxw.geekproject.map
 * @desciption
 */
class LocationFactory(val locationType: Int, val mContext: Context, val isOnceLocation: Boolean, val myILocation: MyILocation) {

    var iLocation: ILocation? = null

    companion object Factory {
        fun create(locationType: Int, mContext: Context, isOnceLocation: Boolean, myILocation: MyILocation): LocationFactory
                = LocationFactory(locationType, mContext, isOnceLocation, myILocation)
    }

    init {
        when (locationType) {
            LocationTypeMode.AMapMode -> iLocation = AMapLocationManager(mContext, myILocation, isOnceLocation)
            LocationTypeMode.BaiduMapMode -> iLocation = BMapLocationManager(mContext, myILocation, isOnceLocation)
        }
    }

}