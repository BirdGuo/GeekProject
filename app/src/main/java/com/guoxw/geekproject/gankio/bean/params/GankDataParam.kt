package com.guoxw.geekproject.gankio.bean.params

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by guoxw on 2017/10/31 0031.
 */
data class GankDataParam(val type: String, val number: Int, val page: Int) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(type)
        writeInt(number)
        writeInt(page)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GankDataParam> = object : Parcelable.Creator<GankDataParam> {
            override fun createFromParcel(source: Parcel): GankDataParam = GankDataParam(source)
            override fun newArray(size: Int): Array<GankDataParam?> = arrayOfNulls(size)
        }
    }
}