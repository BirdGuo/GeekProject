package com.guoxw.geekproject.network.retrofit

import com.guoxw.geekproject.base.BaseBaseView
import com.guoxw.geekproject.base.BaseView
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.functions.Action

/**
 * Created by guoxw on 2017/11/20 0020.
 */
class MyAction(val view: BaseBaseView) : Action {
    override fun run() {
        view.getDataComplete()
    }
}