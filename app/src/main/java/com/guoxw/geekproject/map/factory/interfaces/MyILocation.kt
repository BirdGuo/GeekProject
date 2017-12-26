package com.guoxw.geekproject.map.factory.interfaces

import com.guoxw.geekproject.map.bean.MyLocation

/**
 * @auther guoxw
 * @date 2017/12/26 0026
 * @package com.guoxw.geekproject.map.factory.interfaces
 * @desciption
 */
interface MyILocation {

    fun locationSuccess(myLocation: MyLocation)

    fun locationFail(error: String)
}