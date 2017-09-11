package com.guoxw.geekproject.gankio.api.service

import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by gxw on 17-5-24.
 * @auther gxw
 * @date 17-5-24
 * @desciption
 * @package ${PACKAGE_NAME}
 */
interface GankIOService {

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * @param type
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param number
     * 请求个数： 数字，大于0
     * @param page
     * 第几页：数字，大于0
     *
     * 测试成功
     */
    @GET("data/{type}/{number}/{page}")
    fun getGankIOData(@Path("type") type: String, @Path("number") number: Number, @Path("page") page: Int): Flowable<GankResponse<MutableList<GankData>>>


    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * @param year  年
     * @param month 月
     * @param date  日
     *
     */
    @GET("day/{year}/{month}/{date}")
    fun getGankDayData(@Path("year") year: String, @Path("month") month: String, @Path("date") date: String): Flowable<GankResponse<GankDayData>>

    /**
     * 获取发过干货日期接口
     */
    @GET("day/history")
    fun getGankHistoryDate(): Flowable<GankResponse<MutableList<String>>>

}