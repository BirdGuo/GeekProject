package com.guoxw.geekproject.map.factory.manager

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.guoxw.geekproject.map.bean.MyLocation
import com.guoxw.geekproject.map.factory.ILocation
import com.guoxw.geekproject.map.factory.interfaces.MyILocation

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.factory.manager
 * @desciption 高德定位方法
 */
class AMapLocationManager(
        /**
         * 上下文
         */
        val mContext: Context,
        /**
         * 定位回调
         */
        val myILocation: MyILocation,
        /**
         * 是否单次定位
         */
        val isOnceLocation: Boolean) : ILocation, AMapLocationListener {

    /**
     * 定位终端
     */
    private var mLocationClient: AMapLocationClient? = null

    /**
     * 定位参数
     */
    private val mLocationOption: AMapLocationClientOption = AMapLocationClientOption()

    init {
        //实例化定位终端
        mLocationClient = AMapLocationClient(mContext)
        //设置定位监听
        mLocationClient!!.setLocationListener(this)

    }

    override fun initLcationOption(time: Long) {
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        // 设置定位间隔
        mLocationOption.interval = time
        //需要位置信息
        mLocationOption.isNeedAddress = true
        //设置单次定位
        mLocationOption.isOnceLocation = isOnceLocation
        //设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
    }

    override fun startLocation() {
        //启动定位
        mLocationClient!!.startLocation()
    }

    override fun stopLocation() {
        when (isOnceLocation) {//判断是否单次定位
            true -> return
            false -> mLocationClient!!.stopLocation()
        }
    }

//    override fun isLocating(): Boolean {
//        return true
//    }
//
//    override fun isLocatingSuccess(): Boolean {
//        return true
//    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {

        when (isOnceLocation) {//如果是单次定位就停止定位
            true -> mLocationClient!!.stopLocation()
        }

        if (aMapLocation!!.errorCode == 0) {
            //定位成功页面回调
            myILocation.locationSuccess(MyLocation(aMapLocation.latitude, aMapLocation.longitude, aMapLocation.address,
                    aMapLocation.street, aMapLocation.country, aMapLocation.city, aMapLocation.province))
        } else {
            //定位失败页面回调
            myILocation.locationFail(aMapLocation.errorInfo)
        }

    }
}