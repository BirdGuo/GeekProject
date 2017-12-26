package com.guoxw.geekproject

import android.os.Bundle
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.LocationTypeMode
import com.guoxw.geekproject.map.bean.MyLocation
import com.guoxw.geekproject.map.factory.LocationFactory
import com.guoxw.geekproject.map.factory.interfaces.MyILocation
import com.guoxw.geekproject.utils.LogUtil

class SettingActivity : BaseToolbarActivity(), MyILocation {
    var create: LocationFactory? = null

    override fun locationSuccess(myLocation: MyLocation) {
        LogUtil.i("GXW", "----locationSuccess-----:".plus(myLocation.province).plus(myLocation.city).plus(myLocation.area).plus(myLocation.street).plus(myLocation.address).plus(myLocation.lat).plus(myLocation.lng).plus(myLocation))
    }

    override fun locationFail(error: String) {
        LogUtil.i("GXW", "----locationFail-----:".plus(error))
    }

    override fun getContentLayoutId(): Int = R.layout.activity_setting

    override fun initUI(savedInstanceState: Bundle?) {
    }

    override fun initData() {

        create = LocationFactory.create(LocationTypeMode.BaiduMapMode, this, true, this)
        create!!.iLocation!!.initLcationOption(2000)
        create!!.iLocation!!.startLocation(true)
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        super.onDestroy()
        create!!.iLocation!!.stopLocation()
    }

}
