package com.guoxw.geekproject.base

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.base
 */
interface BaseView<in T> {

    /**
     * 刷新页面
     * @param mData 数据
     */
    fun reflashView(mData: T)

    /**
     * 获取获取数据失败
     * @param error 错误提示语
     */
    fun getDataFail(error: String)

    /**
     * 获取数据完成
     */
    fun getDataComplete()
}