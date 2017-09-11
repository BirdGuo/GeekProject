package com.guoxw.geekproject.gankio.bean

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArray(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(_id)
        writeString(createAt)
        writeString(desc)
        writeStringArray(images)
        writeString(publishedAt)
        writeString(source)
        writeString(type)
        writeString(url)
        writeInt((if (used) 1 else 0))
        writeString(who)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GankData> = object : Parcelable.Creator<GankData> {
            override fun createFromParcel(source: Parcel): GankData = GankData(source)
            override fun newArray(size: Int): Array<GankData?> = arrayOfNulls(size)
        }
    }
}