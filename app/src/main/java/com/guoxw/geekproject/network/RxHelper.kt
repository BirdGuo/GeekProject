package com.guoxw.geekproject.network

import com.guoxw.geekproject.gankio.data.responses.GankResponse
import rx.Observable
import rx.Subscriber
import rx.functions.Func1


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
    fun <T> handleResult(): Observable.Transformer<GankResponse<T>, T> {
        return object : Observable.Transformer<GankResponse<T>, T> {
            override fun call(t: Observable<GankResponse<T>>?): Observable<T> {

                return t!!.flatMap(object : Func1<GankResponse<T>, Observable<out T>> {
                    override fun call(t: GankResponse<T>?): Observable<out T> {

                        if (t!!.error) {//无数据
                            return Observable.error(ApiException(0x0003))
                        } else {
                            return createData(t.results)
                        }

                    }
                })

            }
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
    fun <T> createData(data: T): Observable<T> {
        return Observable.create(object : Observable.OnSubscribe<T> {
            override fun call(t: Subscriber<in T>?) {
                try {
                    t!!.onNext(data)
                    t.onCompleted()
                } catch (e: Exception) {
                    t!!.onError(e)
                }
            }

        })
    }

}