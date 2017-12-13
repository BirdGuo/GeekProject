package com.guoxw.geekproject.gankio.api.resetApi

import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.service.GankIOService
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.network.ApiClient
import io.reactivex.Flowable

/**
 * Created by gxw on 17-5-24.
 * @auther gxw
 * @date 17-5-24
 * @desciption
 * @package ${PACKAGE_NAME}
 */
object GankIOResetApi : GankIOApi {


    /**
     * 分类请求数据
     *
     * @param gankDataParam 分页请求参数
     */
    override fun getGankIOData(gankDataParam: GankDataParam): Flowable<GankResponse<MutableList<GankData>>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankIOData(gankDataParam.type, gankDataParam.number, gankDataParam.page)

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * @param gankDayDataParam 每日数据请求参数
     *
     */
    override fun getGankDayData(gankDayDataParam: GankDayDataParam): Flowable<GankResponse<GankDayData>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankDayData(gankDayDataParam.year, gankDayDataParam.month, gankDayDataParam.date)

    /**
     * 获取发过干货日期接口
     */
    override fun getGankHistoryDate(): Flowable<GankResponse<MutableList<String>>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankHistoryDate()

}