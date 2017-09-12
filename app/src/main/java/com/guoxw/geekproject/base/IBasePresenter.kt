package com.guoxw.geekproject.base

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.gankio.network.utils.Callback
import io.reactivex.Flowable
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.base
 */
interface IBasePresenter<T> {

    fun attachView(lifeSubscription: LifeSubscription)

    /**
     * 取消绑定
     */
    fun detachView()

    /**
     * @param observable
     * @param callBack
     *
     * @hide
     */
    @Deprecated("这个方法过时了", replaceWith = ReplaceWith("invoke(observable: Flowable<T>, consumer: Consumer<T>)"), level = DeprecationLevel.WARNING)
    fun invoke(observable: Flowable<T>, callBack: Callback<T>)

    /**
     * @param observable
     * @param consumer
     */
    fun invoke(observable: Flowable<T>, consumer: Consumer<T>)

    fun checkState(list: List<*>)
}