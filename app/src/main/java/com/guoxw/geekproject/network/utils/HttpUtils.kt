package com.guoxw.gankio.network.utils

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.gankio.data.responses.GankResponse
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
    fun <T> invoke(lifeSubscription: LifeSubscription, observable: Flowable<T>, consumer: Consumer<T>) {
        //var target: Stateful? = null
        //做类型检查，我们需要用到 is 关键字，其实跟 Java 里的 instanceOf 一样。
        //if (lifeSubscription is Stateful) {
        //target = lifeSubscription
        ////callback.target = target
        //}

        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        //        if (!NetworkUtils.isAvailableByPing()) {
//            ToastUtils.showLongToast(R.string.error_wifi)
//            //target!!.setState(AppConstants.STATE_ERROR)
//            return
//        }
        val subscribe: Disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
        //subscribe.dispose()//这句话不能加，加了后就直接cancel了

        lifeSubscription.bindCompositeDisposable(subscribe)

    }

    /**
     *     //添加线程管理并订阅
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber,String cacheKey,boolean isSave, boolean forceRefresh) {
    //数据预处理
    Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult();
    //重用操作符
    Observable observable = ob.compose(result)
    .doOnSubscribe(new Action0() {
    @Override
    public void call() {
    //显示Dialog和一些其他操作
    subscriber.showProgressDialog();
    }
    });
    //缓存
    RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe(subscriber);

    }
     */

    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param cacheKey
     * @param isSave
     * @param forceRefresh
     */
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


//        val observable: Observable<T> = ob.compose<GankResponse<T>>(result).doOnSubscribe(object : Action0 {
//            override fun call() {
//
//
//
//            }
//
//        })

    }


}