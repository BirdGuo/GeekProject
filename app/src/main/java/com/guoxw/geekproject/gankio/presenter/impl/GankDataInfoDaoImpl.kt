package com.guoxw.geekproject.gankio.presenter.impl

import android.content.Context
import com.blankj.utilcode.utils.NetworkUtils
import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BasePresenter
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.dao.GankDataInfoDao
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.functions.Consumer

/**
 * Created by guoxw on 2017/11/16 0016.
 */
class GankDataInfoDaoImpl(val mContext: Context, val lifeSubscription: LifeSubscription, val mView: GankDataInfoDao.View)
    : BasePresenter<GankDayData>(), GankDataInfoDao.Presenter {

    val gankIOResetApi = GankIOResetApi

    override fun fetchDayData(gankDayDataParam: GankDayDataParam) {

        attachView(lifeSubscription)
        if (NetworkUtils.isConnected()) {
            invoke(gankIOResetApi.getGankDayData(gankDayDataParam), Consumer {

                data ->
                val result = data.results
                checkState(result)

                mView!!.reflashView(result)

            }, Consumer { throwable ->
                LogUtil.e("GXW", "11111message:".plus(throwable.message))
                mView.getDataFail(throwable.message!!)
            }, MyAction(mView))
        } else {
            mView!!.getDataFail(mContext.getString(R.string.error_wifi))
        }

    }
}