package com.guoxw.geekproject.gankio.presenter.dao

import com.guoxw.geekproject.base.BaseView
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import com.guoxw.geekproject.gankio.ui.views.IGankDataView

/**
 * Created by guoxw on 2017/11/15 0015.
 */
interface GankDataDao {


    /**
     * 页面绑定数据
     */
    interface view : IGankDataView<GankResponse<MutableList<GankData>>>

    /**
     * 数据接口
     */
    interface presenter {

        /**
         * 分类请求数据
         * @param gankDataParam
         */
        fun fetchGankData(gankDataParam: GankDataParam)

        /**
         * 获取历史记录
         */
        fun fetchGankHistory()

    }
}