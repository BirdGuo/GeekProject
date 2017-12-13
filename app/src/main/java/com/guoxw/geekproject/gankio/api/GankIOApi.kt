package com.guoxw.geekproject.gankio.api

import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
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
     * @param gankDataParam 分页请求参数
     */
    fun getGankIOData(gankDataParam: GankDataParam): Flowable<GankResponse<MutableList<GankData>>>

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * @param gankDayDataParam 每日数据请求参数
     *
     */
    fun getGankDayData(gankDayDataParam: GankDayDataParam): Flowable<GankResponse<GankDayData>>

    /**
     * 获取发过干货日期接口
     */
    fun getGankHistoryDate(): Flowable<GankResponse<MutableList<String>>>
}