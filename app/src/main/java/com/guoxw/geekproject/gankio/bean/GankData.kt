package com.guoxw.geekproject.gankio.bean

import java.io.Serializable
import java.util.*

/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.gankio.bean
 */
data class GankData(
        //id
        var _id: String,
        //创建时间
        var createAt: String = "",
        var desc: String = "",
        var images: Array<String>?,
        var publishedAt: String,
        var source: String,
        var type: String,
        var url: String,
        var used: Boolean,
        var who: String
) : Serializable {

    override fun toString(): String {
        return "GankData(_id='$_id', createAt='$createAt', desc='$desc', images=${Arrays.toString(images)}, publishedAt='$publishedAt', source='$source', type='$type', url='$url', used=$used, who='$who')"
    }
}