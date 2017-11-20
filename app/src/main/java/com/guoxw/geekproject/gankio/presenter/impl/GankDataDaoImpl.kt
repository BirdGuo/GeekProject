package com.guoxw.geekproject.gankio.presenter.impl

import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.base.BasePresenter
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.network.ApiException
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.network.retrofit.MySubscription
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/11/15 0015.
 */
class GankDataDaoImpl(val lifeSubscription: LifeSubscription, val mView: GankDataDao.View)
    : BasePresenter<GankResponse<MutableList<GankData>>>(), GankDataDao.Presenter {

    val gankIOResetApi = GankIOResetApi


    override fun fetchGankHistory() {
        attachView(lifeSubscription)
        invoke(gankIOResetApi.getGankHistoryDate(), Consumer {

            data ->
            val result = data.results
            checkState(result)
            if (data.error) {
                mView.getHisFail(ApiException(0x0003).message!!)
            } else {
                mView.getHisSuccess(data.results)
            }

        }, Consumer { throwable ->
            LogUtil.e("GXW", "message:".plus(throwable.message))
        }, MyAction(), MySubscription())
    }

    override fun fetchGankData(gankDataParam: GankDataParam) {
        attachView(lifeSubscription)
        invoke(gankIOResetApi.getGankIOData(gankDataParam), Consumer { data ->
            val result = data.results
            checkState(result)
            if (data.error) {//无数据
                mView.getDataFail(ApiException(0x0003).message!!)
            } else {//正常
                mView.reflashView(data)
            }
        })
    }

}