package com.guoxw.geekproject.network.utils

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by guoxw on 2017/11/20 0020.
 */
class MySubscriber<T> : Subscriber<T> {
    override fun onNext(t: T) {
    }

    override fun onSubscribe(s: Subscription?) {
    }

    override fun onError(t: Throwable?) {
    }

    override fun onComplete() {
    }
}