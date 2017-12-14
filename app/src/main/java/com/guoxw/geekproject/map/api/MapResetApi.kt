package com.guoxw.geekproject.map.api

import android.content.Context
import rx.Observable

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.api
 * @desciption
 */
object MapResetApi : MapApi {
    override fun readStationsFromAsset(mContext: Context, name: String): Observable<String> {
        return Observable.create { t ->
            t.onStart()

            t.onNext()
            t.onCompleted()
        }
    }

    override fun addStationsToMap() {
    }

    override fun saveStationsToDB() {
    }

}