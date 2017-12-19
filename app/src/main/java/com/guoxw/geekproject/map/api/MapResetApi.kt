package com.guoxw.geekproject.map.api

import android.content.Context
import com.google.gson.Gson
import com.guoxw.geekproject.map.bean.MyStations
import com.guoxw.geekproject.utils.FileUtil
import com.raizlabs.android.dbflow.kotlinextensions.save
import io.reactivex.Observable


/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.api
 * @desciption
 */
object MapResetApi : MapApi {

    override fun readStationsFromAsset(mContext: Context, name: String): Observable<String> {
        return Observable.create { t ->
            //读取文件
            val assetToString = FileUtil.assetToString(mContext, name)
            val gson = Gson()
            val myStations = gson.fromJson(assetToString, MyStations::class.java)
            myStations.save()
            // myStations.stations.save()
            myStations.stations.asSequence()
                    .forEach {
                        it.save()
                    }
            if (assetToString != null) {
                //传到订阅者
                t.onNext(assetToString)
            } else {
                //无内容
                t.onError(Throwable("File content is null"))
            }
            //完成获取
            t.onComplete()
        }
    }

    override fun addStationsToMap() {
    }

    override fun saveStationsToDB() {
    }

}