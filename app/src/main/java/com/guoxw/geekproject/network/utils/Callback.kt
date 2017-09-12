package com.guoxw.gankio.network.utils

import com.blankj.utilcode.utils.NetworkUtils
import com.blankj.utilcode.utils.ToastUtils
import com.guoxw.gankio.network.Stateful
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseView
import com.guoxw.geekproject.constatnt.AppConstants
import com.guoxw.geekproject.utils.LogUtil
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by guoxw on 2017/6/14 0014.
 *
 * 在这儿将泛型传入
 */
open class Callback<T> : Subscriber<T> {

    var target: Stateful? = null

    override fun onNext(value: T) {
        LogUtil.i("MainActivity", "-------------onNext-----------")
        target!!.setState(AppConstants.STATE_SUCCESS)
        onResponse()
        onResponse(value)
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable?) {
        LogUtil.i("MainActivity", "-------------onError-----------")
        e!!.printStackTrace()
        onFail()
    }

    override fun onSubscribe(subscription: Subscription) {
    }

    fun detachView() {
        if (target != null)
            target = null
    }

    open fun onResponse(data: T) {
        /**
         * 如果喜欢统一处理成功回掉也是可以的。
         * 不过获取到的数据都是不规则的，理论上来说需要判断该数据是否为null或者list.size()是否为0
         * 只有不成立的情况下，才能调用成功方法refreshView/()。如果统一处理就放在每个refreshView中处理。
         */
        (target as BaseView<T>).reflashView(data)
    }

    fun onResponse() {}

    fun onFail() {
        if (NetworkUtils.isAvailableByPing()) {
            ToastUtils.showLongToast(R.string.error_wifi)
            target!!.setState(AppConstants.STATE_ERROR)
            return
        }
        ToastUtils.showLongToast(R.string.error_lazy)
        target!!.setState(AppConstants.STATE_EMPTY)
    }

}