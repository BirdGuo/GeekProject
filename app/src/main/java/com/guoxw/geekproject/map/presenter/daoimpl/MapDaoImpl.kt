package com.guoxw.geekproject.map.presenter.daoimpl

import android.content.Context
import com.guoxw.geekproject.map.api.MapResetApi
import com.guoxw.geekproject.map.presenter.dao.MapDao
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.presenter.daoimpl
 * @desciption
 */
class MapDaoImpl(val viewFile: IFileView, val viewMap: IMapView) : MapDao.Presenter {

    private val mapRestApi = MapResetApi

    override fun readStationsFromAsset(mContext: Context, name: String) {

        mapRestApi.readStationsFromAsset(mContext, name).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    //这儿相当于主线程了
                    //在这儿做save操作会卡主线程
                    //完成读取
                    viewFile.readFileToStringSuccess(true)
                }, { error ->
                    //读取失败
                    viewFile.readFileToStringFail(error.message!!)
                }, {
                    //完成读取
                    viewFile.readFileToStringComplete()
                })

    }

    override fun addStationsToMap() {
    }

    override fun saveStationsToDB() {
    }

}