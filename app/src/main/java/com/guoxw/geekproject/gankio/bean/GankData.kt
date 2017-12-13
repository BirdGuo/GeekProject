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
        private var _id: String,
        //创建时间
        private var createAt: String = "",
        var desc: String = "",
        private var images: Array<String>?,
        var publishedAt: String,
        var source: String,
        var type: String,
        var url: String,
        var used: Boolean,
        private var who: String
) : Serializable {

    override fun toString(): String {
        return "GankData(_id='$_id', createAt='$createAt', desc='$desc', images=${Arrays.toString(images)}, publishedAt='$publishedAt', source='$source', type='$type', url='$url', used=$used, who='$who')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GankData

        if (_id != other._id) return false
        if (createAt != other.createAt) return false
        if (desc != other.desc) return false
        if (!Arrays.equals(images, other.images)) return false
        if (publishedAt != other.publishedAt) return false
        if (source != other.source) return false
        if (type != other.type) return false
        if (url != other.url) return false
        if (used != other.used) return false
        if (who != other.who) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + createAt.hashCode()
        result = 31 * result + desc.hashCode()
        result = 31 * result + (images?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + publishedAt.hashCode()
        result = 31 * result + source.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + used.hashCode()
        result = 31 * result + who.hashCode()
        return result
    }
}