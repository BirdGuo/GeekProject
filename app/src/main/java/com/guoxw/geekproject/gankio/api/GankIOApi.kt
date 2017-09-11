package com.guoxw.geekproject.gankio.api

import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import io.reactivex.Flowable

/**
 * Created by guoxw on 2017/5/22.
 * @auther guoxw
 * @date 2017/5/22
 * @desciption
 * @package ${PACKAGE_NAME}
 */
interface GankIOApi {

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
    fun getGankIOData(type: String, number: Number, page: Int): Flowable<GankResponse<MutableList<GankData>>>

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * @param year  年
     * @param month 月
     * @param date  日
     *
     */
    fun getGankDayData(year: String, month: String, date: String): Flowable<GankResponse<GankDayData>>

    /**
     * 获取发过干货日期接口
     */
    fun getGankHistoryDate(): Flowable<GankResponse<MutableList<String>>>
}