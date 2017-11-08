package com.guoxw.geekproject.gankio.ui.views

/**
 * Created by guoxw on 2017/11/7 0007.
 */
interface IWebView {

    fun showProgressBar(progress: Int)

    fun setWebTitle(title: String)

    fun openFailed()

}