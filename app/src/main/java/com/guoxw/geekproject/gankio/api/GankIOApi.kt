package com.guoxw.geekproject.gankio.api

import com.guoxw.geekproject.gankio.bean.GankData
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

}