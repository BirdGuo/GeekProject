package com.guoxw.geekproject.gankio.presenter.dao

import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView

/**
* @auther guoxw
* @date 2017/11/16 0016
* @package ${PACKAGE_NAME}
* @desciption
*/
interface GankDataInfoDao {

    /**
     * 页面回调
     */
    interface View : IGankDayDataView

    /**
     * 数据获取接口
     */
    interface Presenter {

        /**
         * 获取每日数据
         */
        fun fetchDayData(gankDayDataParam: GankDayDataParam)

    }

}