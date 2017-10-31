package com.guoxw.geekproject.gankio.presenter

import android.content.Context
import android.support.v4.content.ContextCompat
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.ui.views.IGankDataView
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by guoxw on 2017/10/31 0031.
 */
class GankDataPresenter(val gankDataView: IGankDataView, val context: Context) {

    //网络请求
    private val gankIOApi = GankIOResetApi

    /**
     * 分类请求数据
     *
     */
    fun initGankData(gankDataParam: GankDataParam) {

        gankIOApi.getGankIOData(gankDataParam).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    //成功
                    if (!res.error) {
                        gankDataView.reflashView(res.results)
                    } else {//无数据
                        gankDataView.getDataFail(context.getString(R.string.error_network))
                    }
                }, { e ->
                    //获取失败
                    gankDataView.getDataFail(e.message!!)
                }, {
                    //获取完成
                    gankDataView.getDataComplete()
                })

    }

}