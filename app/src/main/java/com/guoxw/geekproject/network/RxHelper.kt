package com.guoxw.geekproject.network

import com.guoxw.geekproject.enums.ActivityLifeCycleEvent
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject


/**
 * Created by guoxw on 2017/11/13 0013.
 *
 * 对返回的结果进行预处理
 */
object RxHelper {

    /**
     * 对结果进行预处理
     *
     * @param T
     *
     * @return
     */
    fun <T> handleResult(event: ActivityLifeCycleEvent, lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>)
            : Observable.Transformer<GankResponse<T>, T> {

        return Observable.Transformer { tObservable ->
            val compareLifecycleObservable: Observable<ActivityLifeCycleEvent>
                    = lifecycleSubject.takeFirst { activityLifeCycleEvent -> activityLifeCycleEvent!! == event }


            tObservable!!.flatMap { t ->
                if (t!!.error) {//无数据
                    Observable.error(ApiException(0x0003))
                } else {
                    createData(t.results)
                }
            }.takeUntil(compareLifecycleObservable)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
        }
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param T
     *
     * @return
     */
    private fun <T> createData(data: T): Observable<T> {
        return Observable.create { t ->
            try {
                t!!.onNext(data)
                t.onCompleted()
            } catch (e: Exception) {
                t!!.onError(e)
            }
        }
    }

}