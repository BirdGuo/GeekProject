package com.guoxw.geekproject.gankio.presenter

import android.content.Context
import android.util.Log
import com.blankj.utilcode.utils.NetworkUtils
import com.guoxw.geekproject.R
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
class GankDataInfoPresenter(val gankDayDataView: IGankDayDataView, val mContext: Context) {

    //接口
    private val gankIOApi: GankIOApi = GankIOResetApi

    fun initDayData(gankDayDataParam: GankDayDataParam) {

        //判断网络是否连接
        if (NetworkUtils.isConnected()) {//已连接
            gankIOApi.getGankDayData(gankDayDataParam).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribe({ res ->
                        //成功
                        if (res.error) {//无数据
                            gankDayDataView.getDataFail(mContext.getString(R.string.error_no_data))
                        } else {
                            gankDayDataView.reflashView(res.results)
                        }
                    }, { error ->
                        //获取失败
                        gankDayDataView.getDataFail(mContext.getString(R.string.error_no_data))
                    }, {
                        //获取完成
                        gankDayDataView.getDataComplete()
                    })
        } else {
            gankDayDataView.getDataFail(mContext.getString(R.string.error_wifi))
        }

    }


}