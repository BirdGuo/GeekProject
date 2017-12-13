package com.guoxw.geekproject.base

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.base
 */
interface BaseView<in T> : BaseBaseView {

    /**
     * 刷新页面
     * @param mData 数据
     */
    fun reflashView(mData: T)

}