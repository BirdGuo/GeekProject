package com.guoxw.geekproject.base

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.gankio.network.Stateful
import com.guoxw.gankio.network.utils.Callback
import com.guoxw.gankio.network.utils.HttpUtils
import com.guoxw.geekproject.constatnt.AppConstants
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.network.retrofit.MySubscription
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * Created by gxw on 17-11-13.
 */
open abstract class BasePresenter<D> : IBasePresenter {


    /**
     * 具体页面
     */
    protected var mReferenceView: Reference<BaseView<D>>? = null
    /**
     * 添加监听页面
     */
    var view: BaseView<D>? = null
    /**
     * CompositeDisposable管理订阅
     */
    protected var compositeDisposable: CompositeDisposable? = null

    /**
     *  1、Kotlin有两种类型：一个是非空引用类型，一个是可空引用类型。
     *  2、对于可空引用，如果希望调用它的成员变量或者成员函数，直接调用会出现编译错误，有三种方法可以调用：
     *（1）在调用前，需要先检查，因为可能为null
     *（2）使用b?.length的形式调用，如果b为null，返回null，否则返回b.length
     *（3）使用b!!.length()的形式调用，如果b为null，抛出空指针异常，否则返回b.length
     */
    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null)
            compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(disposable)
    }

    /**
     * 取消订阅
     */
    protected fun unDisposable() {
        //它的返回值有两种可能，如果b不为null，返回b.length()，否则，抛出一个空指针异常，如果b为null，你不想返回null，而是抛出一个空指针异常，你就可以使用它。
        if (compositeDisposable != null)
            compositeDisposable!!.clear()
    }

    override fun checkState(list: MutableList<*>) {
        //列表为空并且页面线程处于安全状态
        if (list.isEmpty()) {
            (view as Stateful).setState(AppConstants.STATE_EMPTY)
        }
    }

    override fun checkState(any: Any) {
        if (any != null) {
            (view as Stateful).setState(AppConstants.STATE_EMPTY)
        }
    }

    override fun attachView(lifeSubscription: LifeSubscription) {
        /**
         * 弱引用。弱引用最主要的特点是对象会在gc的时候立即回收，不考虑内存实际占用。所以在某些短生命周期的对象也是适合做缓存的。
         * java.lang.ref.WeakReference
         *
         * 特点：
         * 弱引用使用 get() 方法取得对象的强引用从而访问目标对象。
         * 一旦系统内存回收，无论内存是否紧张，弱引用指向的对象都会被回收。
         * 弱引用也可以避免 Heap 内存不足所导致的异常。
         */
        mReferenceView = WeakReference(lifeSubscription as BaseView<D>)
        //获取页面对象
        view = mReferenceView!!.get()
    }

    override fun detachView() {
        view = null
        unDisposable()
    }

    override fun <A> invoke(observable: Flowable<A>, next: Consumer<A>) {
        HttpUtils.invoke(view as LifeSubscription, observable, next)
    }

    override fun <A> invoke(observable: Flowable<A>, next: Consumer<A>, error: Consumer<Throwable>, complete: MyAction, subscription: MySubscription) {

        HttpUtils.invoke(view as LifeSubscription, observable, next, error, complete, subscription)

    }

    override fun <A> invoke(observable: Flowable<A>, next: Consumer<A>, error: Consumer<Throwable>, complete: MyAction) {
        HttpUtils.invoke(view as LifeSubscription, observable, next, error, complete)
    }
}