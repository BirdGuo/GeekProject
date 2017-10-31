package com.guoxw.geekproject.gankio.presenter

import android.util.Log
import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.gankio.presenter
 */
class GankDataInfoPresenter(val gankDayDataView: IGankDayDataView) {

    //接口
    private val gankIOApi: GankIOApi = GankIOResetApi

    fun initDayData(gankDayDataParam: GankDayDataParam) {

        gankIOApi.getGankDayData(gankDayDataParam).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    //成功
                    if (res.error) {//无数据
                        gankDayDataView.getDataFail("网络错误")
                    } else {
                        gankDayDataView.reflashView(res.results)
                    }
                }, { error ->
                    //获取失败
                    gankDayDataView.getDataFail(error.message.toString())
                }, {
                    //获取完成
                    gankDayDataView.getDataComplete()
                })

    }


}