package com.guoxw.geekproject.gankio.ui.views

import com.guoxw.geekproject.base.BaseView

/**
 * Created by guoxw on 2017/10/31 0031.
 */
interface IGankDataView<T> : BaseView<T> {

    /**
     * 获取历史日期
     * @param dates 日期数组 YYYY-MM-DD
     */
    fun getHisSuccess(dates: MutableList<String>)

    /**
     * 获取日期失败
     * @param error 失败提示语
     */
    fun getHisFail(error: String)

}