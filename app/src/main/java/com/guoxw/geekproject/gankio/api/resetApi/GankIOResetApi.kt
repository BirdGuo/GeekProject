package com.guoxw.geekproject.gankio.api.resetApi

import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.service.GankIOService
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.data.responses.GankResponse
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
     * @param type
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param number
     * 请求个数： 数字，大于0
     * @param page
     * 第几页：数字，大于0
     */
    override fun getGankIOData(type: String, number: Number, page: Int): Flowable<GankResponse<MutableList<GankData>>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankIOData(type, number, page)

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * @param year  年
     * @param month 月
     * @param date  日
     *
     */
    override fun getGankDayData(year: String, month: String, date: String): Flowable<GankResponse<GankDayData>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankDayData(year, month, date)

    /**
     * 获取发过干货日期接口
     */
    override fun getGankHistoryDate(): Flowable<GankResponse<MutableList<String>>>
            = ApiClient.retrofit.create(GankIOService::class.java).getGankHistoryDate()

}