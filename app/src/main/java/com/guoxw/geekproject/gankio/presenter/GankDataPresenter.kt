package com.guoxw.geekproject.gankio.presenter

import android.content.Context
import com.blankj.utilcode.utils.NetworkUtils
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.gankio.ui.views.IGankDataView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
* @auther guoxw
* @date 2017/10/31 0031
* @package ${PACKAGE_NAME}
* @desciption
*/
class GankDataPresenter(private val gankDataView: IGankDataView<GankResponse<MutableList<GankData>>>, val context: Context) {

    //网络请求
    private val gankIOApi = GankIOResetApi

    /**
     * 分类请求数据
     *
     */
    fun initGankData(gankDataParam: GankDataParam) {

        if (NetworkUtils.isConnected()) {
            gankIOApi.getGankIOData(gankDataParam)
                    .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribe({ res ->
                        //成功
                        if (!res.error) {
                            gankDataView.reflashView(res)
                        } else {//无数据
                            gankDataView.getDataFail(context.getString(R.string.error_no_data))
                        }
                    }, { e ->
                        //获取失败
                        gankDataView.getDataFail(context.getString(R.string.error_no_data))
                    }, {
                        //获取完成
                        gankDataView.getDataComplete()
                    })
        } else {
            gankDataView.getDataFail(context.getString(R.string.error_wifi))
        }
    }

    /**
     * 获取历史记录
     */
    fun initGankHistory() {
        if (NetworkUtils.isConnected()) {
            gankIOApi.getGankHistoryDate().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribe({ res ->
                        if (!res.error) {
                            gankDataView.getHisSuccess(res.results)
                        } else {
                            gankDataView.getDataFail(context.getString(R.string.error_no_data))
                        }
                    }, { e ->
                        //获取失败
                        gankDataView.getDataFail(context.getString(R.string.error_no_data))
                    }, {
                        gankDataView.getDataComplete()
                    })
        } else {
            gankDataView.getDataFail(context.getString(R.string.error_wifi))
        }
    }


}