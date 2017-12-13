package com.guoxw.geekproject.gankio.bean

import java.io.Serializable

/**
 * Created by guoxw on 2017/9/8 0008.
 * @auther guoxw
 * @date 2017/9/8 0008
 * @desciption
 * @package com.guoxw.geekproject.gankio.bean
 */
data class GankDayData(
        var 拓展资源: MutableList<GankData>,
        var Android: MutableList<GankData>,
        var iOS: MutableList<GankData>,
        var 休息视频: MutableList<GankData>,
        var 前端: MutableList<GankData>,
        var 福利: MutableList<GankData>) : Serializable