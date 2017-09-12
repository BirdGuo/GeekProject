package com.guoxw.geekproject.gankio.presenter

import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankDayDataParam
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
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

    private val gankIOApi: GankIOApi = GankIOResetApi

    fun initDayData(gankDayDataParam: GankDayDataParam) {

        gankIOApi.getGankDayData(gankDayDataParam.year, gankDayDataParam.month, gankDayDataParam.date).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->

                    if (res.error) {
                        gankDayDataView.getDataFail("网络错误")
                    } else {
                        gankDayDataView.reflashView(res.results)
                    }

                }, { error ->
                    gankDayDataView.getDataFail(error.message.toString())
                }, {
                    gankDayDataView.getDataComplete()
                })

    }


}