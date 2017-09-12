package com.guoxw.geekproject.base

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.base
 */
interface BaseView<T> {

    fun reflashView(mData: T)

    fun getDataFail(error: String)

    fun getDataComplete()
}