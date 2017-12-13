package com.guoxw.geekproject.network.retrofit

import com.guoxw.geekproject.base.BaseBaseView
import io.reactivex.functions.Action

/**
* @auther guoxw
* @date 2017/11/20 0020
* @package ${PACKAGE_NAME}
* @desciption
*/
class MyAction(val view: BaseBaseView) : Action {
    override fun run() {
        view.getDataComplete()
    }
}