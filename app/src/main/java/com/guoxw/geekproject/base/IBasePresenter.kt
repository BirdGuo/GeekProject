package com.guoxw.geekproject.base

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.gankio.network.utils.Callback
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.network.retrofit.MySubscription
import io.reactivex.Flowable
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.base
 */
interface IBasePresenter {

    /**
     * 绑定视图订阅
     *
     * @param lifeSubscription
     *
     */
    fun attachView(lifeSubscription: LifeSubscription)

    /**
     * 取消绑定
     */
    fun detachView()

//    /**
//     * @param observable
//     * @param callBack
//     *
//     * @hide
//     */
//    @Deprecated("这个方法过时了", replaceWith = ReplaceWith("invoke(observable: Flowable<T>, consumer: Consumer<T>)"), level = DeprecationLevel.HIDDEN)
//    fun invoke(observable: Flowable<T>, callBack: Callback<T>)

    /**
     * 添加绑定
     *
     * @param observable
     * @param consumer
     *
     */
    @Deprecated("这个方法过时了", replaceWith = ReplaceWith("invoke(observable: Flowable<D>, next: Consumer<D>, error: Consumer<Throwable>, complete: MyAction, subscription: MySubscription)"), level = DeprecationLevel.HIDDEN)
    fun <D> invoke(observable: Flowable<D>, next: Consumer<D>)

    /**
     * 添加绑定
     *
     * @param observable
     * @param consumer
     */
    fun <D> invoke(observable: Flowable<D>, next: Consumer<D>, error: Consumer<Throwable>, complete: MyAction, subscription: MySubscription)

    /**
     * 添加绑定
     *
     * @param observable
     * @param consumer
     */
    fun <D> invoke(observable: Flowable<D>, next: Consumer<D>, error: Consumer<Throwable>, complete: MyAction)

    /**
     * 检查状态
     * @param list
     */
    fun checkState(list: MutableList<*>)

    /**
     * 检查状态
     * @param any
     */
    fun checkState(any: Any)
}