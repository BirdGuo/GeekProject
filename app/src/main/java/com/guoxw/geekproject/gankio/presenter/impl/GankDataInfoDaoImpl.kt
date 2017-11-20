package com.guoxw.geekproject.gankio.presenter.impl

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.base.BasePresenter
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.dao.GankDataInfoDao
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/11/16 0016.
 */
class GankDataInfoDaoImpl(val lifeSubscription: LifeSubscription) : BasePresenter<GankDayData>(), GankDataInfoDao.Presenter {

    val gankIOResetApi = GankIOResetApi

    override fun fetchDayData(gankDayDataParam: GankDayDataParam) {

        attachView(lifeSubscription)
        invoke(gankIOResetApi.getGankDayData(gankDayDataParam), Consumer {

            data->
            val result = data.results
            checkState(result)

            view!!.reflashView(result)

        })

    }
}