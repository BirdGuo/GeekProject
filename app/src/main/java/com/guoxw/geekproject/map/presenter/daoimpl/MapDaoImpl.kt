package com.guoxw.geekproject.map.presenter.daoimpl

import android.content.Context
import com.google.gson.Gson
import com.guoxw.geekproject.map.api.MapResetApi
import com.guoxw.geekproject.map.bean.MyStations
import com.guoxw.geekproject.map.presenter.dao.MapDao
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView
import com.raizlabs.android.dbflow.kotlinextensions.save
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

                    val gson = Gson()
                    val myStations = gson.fromJson(res, MyStations::class.java)
                    val save = myStations.save()
                    //完成读取
                    viewFile.readFileToStringSuccess(save)
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