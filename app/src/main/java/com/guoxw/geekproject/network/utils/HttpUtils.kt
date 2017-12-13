package com.guoxw.geekproject.network.utils

import com.guoxw.geekproject.network.LifeSubscription
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.network.retrofit.MySubscription
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import rx.Observable

/**
 * Created by guoxw on 2017/6/27 0027.
 * @auther guoxw
 * @date 2017/6/27 0027
 * @desciption
 * @package ${PACKAGE_NAME}
 */
object HttpUtils {

    /**
     * @define 你好
     */
    fun <T> invoke(lifeSubscription: LifeSubscription, observable: Flowable<T>,
                   next: Consumer<T>) {
        //var target: Stateful? = null
        //做类型检查，我们需要用到 is 关键字，其实跟 Java 里的 instanceOf 一样。
        //if (lifeSubscription is Stateful) {
        //target = lifeSubscription
        ////callback.target = target
        //}

        val subscribe: Disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next)
        //subscribe.dispose()//这句话不能加，加了后就直接cancel了

        lifeSubscription.bindCompositeDisposable(subscribe)

    }

    /**
     * @define 你好
     */
    fun <T> invoke(lifeSubscription: LifeSubscription, observable: Flowable<T>,
                   next: Consumer<T>, error: Consumer<Throwable>, complete: MyAction, subscription: MySubscription) {
        //var target: Stateful? = null
        //做类型检查，我们需要用到 is 关键字，其实跟 Java 里的 instanceOf 一样。
        //if (lifeSubscription is Stateful) {
        //target = lifeSubscription
        ////callback.target = target
        //}

        val subscribe: Disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error, complete, subscription)
        //subscribe.dispose()//这句话不能加，加了后就直接cancel了

        lifeSubscription.bindCompositeDisposable(subscribe)

    }

    /**
     * @define 你好
     */
    fun <T> invoke(lifeSubscription: LifeSubscription, observable: Flowable<T>,
                   next: Consumer<T>, error: Consumer<Throwable>, complete: MyAction) {
        //var target: Stateful? = null
        //做类型检查，我们需要用到 is 关键字，其实跟 Java 里的 instanceOf 一样。
        //if (lifeSubscription is Stateful) {
        //target = lifeSubscription
        ////callback.target = target
        //}

        val subscribe: Disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error, complete)
        //subscribe.dispose()//这句话不能加，加了后就直接cancel了

        lifeSubscription.bindCompositeDisposable(subscribe)

    }
    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param cacheKey
     * @param isSave
     * @param forceRefresh
     */
    @Deprecated("这个方法暂时还不会写", replaceWith = ReplaceWith("使用invoke代替", "com.guoxw.geekproject.network.utils.HttpUtils.invoke"), level = DeprecationLevel.HIDDEN)
    fun <T> toSubscribe(ob: Observable<GankResponse<T>>, cacheKey: String, isSave: Boolean, forceRefresh: Boolean) {

//        //数据预处理
//        val result: Observable.Transformer<GankResponse<T>, T> = RxHelper.handleResult()
//        //重用操作符
//        val observable = ob.compose(result).doOnSubscribe(object : Action0 {
//            override fun call() {
//
//                //一些其他操作
//
//                Log.i("GXW", "--------toSubscribe-----------")
//
//            }
//        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())


    }


}