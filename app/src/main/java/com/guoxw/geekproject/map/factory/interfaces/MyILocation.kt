package com.guoxw.geekproject.map.factory.interfaces

import com.guoxw.geekproject.map.bean.MyLocation

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.factory.interfaces
 * @desciption
 */
open interface MyILocation {

    /**
     * 定位成功接口回调
     * @param myLocation 定位信息
     */
    fun locationSuccess(myLocation: MyLocation)

    /**
     * 定位失败接口回调
     * @param error 失败信息
     */
    fun locationFail(error: String)
}