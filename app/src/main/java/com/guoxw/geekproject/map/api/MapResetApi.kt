package com.guoxw.geekproject.map.api

import android.content.Context
import com.guoxw.geekproject.utils.FileUtil
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