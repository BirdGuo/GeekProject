package com.guoxw.geekproject.gankio.presenter.impl

import android.content.Context
import com.blankj.utilcode.utils.NetworkUtils
import com.guoxw.geekproject.network.LifeSubscription
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BasePresenter
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.network.ApiException
import com.guoxw.geekproject.network.retrofit.MyAction
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.functions.Consumer

/**
* @auther guoxw
* @date 2017/11/15 0015
* @package ${PACKAGE_NAME}
* @desciption
*/
class GankDataDaoImpl(val mContext: Context, private val lifeSubscription: LifeSubscription, private val mView: GankDataDao.View)
    : BasePresenter<GankResponse<MutableList<GankData>>>(), GankDataDao.Presenter {

    private val gankIOResetApi = GankIOResetApi

    val TAG: String = GankDataDaoImpl::class.java.name

    override fun fetchGankHistory() {
        attachView(lifeSubscription)
        //判断网络是否连接
        if (NetworkUtils.isConnected()) {//已连接
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
            }, MyAction(mView))
        } else {
            mView.getDataFail(mContext.getString(R.string.error_wifi))
        }
    }

    override fun fetchGankData(gankDataParam: GankDataParam) {
        attachView(lifeSubscription)
        if (NetworkUtils.isConnected()) {//已连接
            invoke(gankIOResetApi.getGankIOData(gankDataParam), Consumer { data ->
                val result = data.results
                checkState(result)
                if (data.error) {//无数据
                    mView.getDataFail(ApiException(0x0003).message!!)
                } else {//正常
                    mView.reflashView(data)
                }
            }, Consumer { throwable ->
                LogUtil.e("GXW", "message:".plus(throwable.message))
            }, MyAction(mView))
        } else {
            mView.getDataFail(mContext.getString(R.string.error_wifi))
        }
    }

}