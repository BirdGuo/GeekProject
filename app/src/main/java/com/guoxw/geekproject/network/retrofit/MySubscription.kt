package com.guoxw.geekproject.network.retrofit

import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.functions.Consumer
import org.reactivestreams.Subscription

/**
 * Created by guoxw on 2017/11/20 0020.
 */
class MySubscription : Consumer<Subscription> {
    override fun accept(t: Subscription?) {

        LogUtil.i("GXW", "----------MySubscription--------")

    }
}