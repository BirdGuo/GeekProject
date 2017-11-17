package com.guoxw.geekproject.gankio.presenter.impl

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.base.BasePresenter
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.network.ApiException
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/11/15 0015.
 */
class GankDataDaoImpl(val lifeSubscription: LifeSubscription)
    : BasePresenter<GankResponse<MutableList<GankData>>, GankDataDao.View>(), GankDataDao.Presenter {

    val gankIOResetApi = GankIOResetApi

    override fun fetchGankHistory() {
        attachView(lifeSubscription)
        invoke(gankIOResetApi.getGankHistoryDate(), Consumer {

            data ->
            val result = data.results
            checkState(result)
            if (data.error) {
                view!!.getHisFail(ApiException(0x0003).message!!)
            } else {
                view!!.getHisSuccess(data.results)
            }

        })
    }

    override fun fetchGankData(gankDataParam: GankDataParam) {
        attachView(lifeSubscription)
        invoke(gankIOResetApi.getGankIOData(gankDataParam), Consumer { data ->
            val result = data.results
            checkState(result)
            if (data.error) {//无数据
                view!!.getDataFail(ApiException(0x0003).message!!)
            } else {//正常
                view!!.reflashView(data)
            }
        })
    }

}